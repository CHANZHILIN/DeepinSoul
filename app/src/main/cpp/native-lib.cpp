#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_com_deepinsoul_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "nihao ,this  is Hello from C++";
    return env->NewStringUTF(hello.c_str());
}
