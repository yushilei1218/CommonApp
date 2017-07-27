package com.shileiyu;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;

@AutoService(Processor.class)
public class RetrofitProcessor extends AbstractProcessor {

    private Filer mFileUtils;
    private Elements mElementUtils;
    private Messager mMessager;

    private static final String PROXY_SUFFIX = "Proxy";
    private static final String TASK_ID_FIELD_NAME = "mTaskId";
    private static final String API_IMPL_FIELD_NAME = "mApiImpl";
    private static final String POOL_PATH = "com.yushilei.commonapp.common.retrofit";
    private static final String POOL_NAME = "CallPool";

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        mFileUtils = processingEnv.getFiler();
        mElementUtils = processingEnv.getElementUtils();
        mMessager = processingEnv.getMessager();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotationTypes = new LinkedHashSet<String>();
        annotationTypes.add(RetrofitProxy.class.getCanonicalName());
        return annotationTypes;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        //收集信息
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(RetrofitProxy.class);
        int index = 0;
        for (Element e : elements) {
            if (!e.getKind().isInterface())
                continue;
            index++;
            //MethodSpec
            List<MethodSpec> methodSpecList = new ArrayList<>();
            for (Element enclosedElement : e.getEnclosedElements()) {
                //just process methods in Interface, ignored fields etc.
                if (enclosedElement.getKind() == ElementKind.METHOD) {
                    ExecutableElement methodElement = (ExecutableElement) enclosedElement;
                    MethodInfo methodInfo = generateMethodInfo(methodElement);
                    MethodSpec methodSpec = generateProxyMethod(methodInfo);
                    methodSpecList.add(methodSpec);
                }
            }
            ClassName callPool = ClassName.get(POOL_PATH, POOL_NAME);

            MethodSpec addCall = MethodSpec.methodBuilder("getPool")
                    .addModifiers(Modifier.PRIVATE)
                    .returns(callPool)
                    .addStatement("return null")
                    .build();
            TypeName interfaceTypeName = getTypeName(e);

            MethodSpec constructorMethodSpec = generateConstructorMethod(interfaceTypeName);
            //FieldSpec
            FieldSpec taskIdFieldSpec = generateTaskIdFieldSpec();
            FieldSpec interfaceImplFieldSpec = generateInterfaceImplFieldSpec(interfaceTypeName);

            String generatedProxyClassPath = getOutputPackagePath(e);


            //TypeSpec
            TypeSpec typeSpec = TypeSpec.classBuilder(getSimpleName(e) + PROXY_SUFFIX + index)
                    .addSuperinterface(interfaceTypeName)
                    .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                    .addField(taskIdFieldSpec)
                    .addField(interfaceImplFieldSpec)
                    .addMethod(constructorMethodSpec)
                    .addMethods(methodSpecList)
                    .addMethod(addCall)
                    .build();
            JavaFile javaFile = JavaFile.builder(generatedProxyClassPath, typeSpec)
                    .build();
            try {
                javaFile.writeTo(mFileUtils);
            } catch (IOException ex) {
                mMessager.printMessage(Diagnostic.Kind.ERROR, "fail to write to file.");
                throw new RuntimeException(ex);
            }
        }
        return true;
    }

    private FieldSpec generateInterfaceImplFieldSpec(TypeName interfaceTypeName) {
        return FieldSpec.builder(interfaceTypeName, API_IMPL_FIELD_NAME, Modifier.PRIVATE).build();
    }

    private FieldSpec generateTaskIdFieldSpec() {
        return FieldSpec
                .builder(TypeName.INT, TASK_ID_FIELD_NAME, Modifier.PRIVATE)
                .build();
    }

    private String getOutputPackagePath(Element e) {
        String interfacePath = ((TypeElement) e).getQualifiedName().toString();
        Name simpleName = e.getSimpleName();
        int length = interfacePath.length();
        return interfacePath.substring(0, length - simpleName.length() - 1 - 5);
    }

    private MethodSpec generateConstructorMethod(TypeName interfaceTypeName) {
        String parameterName = "apiImplGeneratedByRetrofit";
        String taskIdParameterName = "taskId";
        return MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC)
                .addParameter(interfaceTypeName, parameterName)
                .addParameter(TypeName.INT, taskIdParameterName)
                .addStatement("$L = $L", API_IMPL_FIELD_NAME, parameterName)
                .addStatement("$L = $L", TASK_ID_FIELD_NAME, taskIdParameterName)
                .build();
    }

    private TypeName getTypeName(Element e) {
        TypeMirror interfaceTypeMirror = e.asType();
        return ClassName.get(interfaceTypeMirror);
    }

    private MethodSpec generateProxyMethod(MethodInfo methodInfo) {

        //Call<T> call = mInterfaceImpl.xxx();
        TypeName proxyMethodReturnType = methodInfo.getTypeName();
        String proxyMethodName = methodInfo.getMethodName();
        String proxyMethodParams = StringUtils.join(methodInfo.getMethodParametersSimple().iterator(), ",");
        String proxyMethodStatement = "$T call = $L.$L($L)";
        //mCallList.add(call);
        String recordCallStatement = "$L.addCall(call," + TASK_ID_FIELD_NAME + ")";
        //return call;
        String returnCallStatement = "return call";

        return MethodSpec.methodBuilder(methodInfo.getMethodName())
                .returns(methodInfo.getTypeName())
                .addModifiers(methodInfo.getMethodModifiers())
                .addParameters(methodInfo.getMethodParameters())
                .addStatement(proxyMethodStatement, proxyMethodReturnType,
                        API_IMPL_FIELD_NAME, proxyMethodName, proxyMethodParams)
                .addStatement(recordCallStatement, "CallPool")
                .addStatement(returnCallStatement)
                .build();
    }

    private MethodInfo generateMethodInfo(ExecutableElement methodElement) {
        //modifiers
        ArrayList<Modifier> methodModifiers = new ArrayList<>();
        methodModifiers.add(Modifier.PUBLIC);
        //name
        String methodName = getSimpleName(methodElement);
        //params
        List<VariableElement> methodParams = new ArrayList<>();
        for (VariableElement typeParameterElement : methodElement.getParameters()) {
            methodParams.add(typeParameterElement);
        }
        //return type
        TypeMirror methodReturnType = methodElement.getReturnType();
        return new MethodInfo().setMethodName(methodName)
                .setMethodModifiers(methodModifiers)
                .setMethodParameters(methodParams)
                .setMethodReturnType(methodReturnType);
    }

    private String getSimpleName(Element element) {
        return element.getSimpleName().toString();
    }
}
