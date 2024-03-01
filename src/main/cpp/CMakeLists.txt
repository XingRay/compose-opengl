cmake_minimum_required(VERSION 3.22.1)

project("MyWindow")

# glm
# https://github.com/g-truc/glm
# set(GLM_INSTALL_DIR D:\\develop\\opengl\\glm\\glm-0.9.9.8)

# opencv
# https://github.com/opencv/opencv
# set(OPENCV_INSTALL_DIR D:\\develop\\opencv\\4.9.0\\android\\OpenCV-android-sdk)

# opencv-mobile
# https://github.com/nihui/opencv-mobile
#set(OPENCV_INSTALL_DIR D:\\develop\\opencv-mobile\\4.8.1\\opencv-mobile-4.8.1-android)

# ncnn
# https://github.com/Tencent/ncnn
# set(NCNN_INSTALL_DIR D:\\develop\\ncnn\\ncnn-20240102\\ncnn-20240102-android-vulkan)

# jdk
# D:\develop\java\jdk\openjdk\jdk-21
set(JDK_HOME_DIR D:\\develop\\java\\jdk\\openjdk\\jdk-21)

# visual studio
set(VS_HOME_DIR C:\\Program Files\\Microsoft Visual Studio\\2022\\Community\\VC\\Tools\\MSVC\\14.39.33519)

################
## set params ##
################

# set(OpenCV_DIR ${OPENCV_INSTALL_DIR}/sdk/native/jni)
# find_package(OpenCV REQUIRED)
# set(OPENCV_INCLUDE ${OPENCV_INSTALL_DIR}/sdk/native/jni/include)
# message("OpenCV_DIR = ${OpenCV_DIR}")
# message("OpenCV_INCLUDE_DIRS = ${OpenCV_INCLUDE_DIRS}")
# message("OpenCV_LIBS = ${OpenCV_LIBS}")


# set(ncnn_DIR ${NCNN_INSTALL_DIR}/${ANDROID_ABI}/lib/cmake/ncnn)
# find_package(ncnn REQUIRED)
# set(NCNN_INCLUDE ${NCNN_INSTALL_DIR}/${ANDROID_ABI}/include)
# message("ncnn_DIR = ${ncnn_DIR}")
# message("ncnn_INCLUDE_DIRS = ${ncnn_INCLUDE_DIRS}")
# message("ncnn_LIBS = ${ncnn_LIBS}")

include_directories(
        # ${GLM_INSTALL_DIR}/glm
        # ${OPENCV_INCLUDE}
        # ${NCNN_INCLUDE}
        ${JDK_HOME_DIR}/include
        ${JDK_HOME_DIR}/include/win32
        ${VS_HOME_DIR}/include

        renderer
)

# glm
# set(BUILD_STATIC_LIBS ON)
# add_subdirectory(${GLM_INSTALL_DIR}/glm/glm ${CMAKE_BINARY_DIR}/vendor/glm)

#link_libraries(
  #       glm_static
#)


file(GLOB_RECURSE SOURCE_FILES "${CMAKE_CURRENT_SOURCE_DIR}/*.cpp")

add_library( # Sets the name of the library.
        ${PROJECT_NAME}

        # Sets the library as a shared library.
        SHARED

        # Provides a relative path to your source file(s).
        ${SOURCE_FILES})

# target_compile_options(${PROJECT_NAME} PRIVATE /MD /LD)

link_directories(
    ${JDK_HOME_DIR}/lib
    ${VS_HOME_DIR}/lib/x64
)

target_link_libraries( # Specifies the target library.
        ${PROJECT_NAME}
        # Links the target library to the log library

        # included in the NDK.
    #     android
      #   camera2ndk
        # mediandk
        # log
        # EGL
        # GLESv3
        # ${OpenCV_LIBS}
        # ncnn

        user32
        gdi32
        opengl32
        ${JDK_HOME_DIR}/lib/jawt.lib
)
