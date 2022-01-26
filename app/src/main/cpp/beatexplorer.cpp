
#include <jni.h>
#include <string>


extern "C"
JNIEXPORT jstring JNICALL
Java_com_ahmadshahwaiz_beatexplorer_data_global_Keys_fourSquareClientId(JNIEnv *env, jobject ) {
    std::string clientId = "0FBYQAHWJL34UNJWBAGETDDQH4LTKULCIQOHVKCWDS2Q0ZWC";
    return env->NewStringUTF(clientId.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_ahmadshahwaiz_beatexplorer_data_global_Keys_fourSquareClientSecret(JNIEnv *env, jobject ) {
    std::string clientSecret = "YPTESEKQITOZBCVUQ1MF0DZIGPZW1AEKHXSX4FU3YENLU0YP";
    return env->NewStringUTF(clientSecret.c_str());
}
