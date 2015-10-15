LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := PVZ
LOCAL_SRC_FILES := PVZ.cpp

include $(BUILD_SHARED_LIBRARY)
