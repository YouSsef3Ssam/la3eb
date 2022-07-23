package com.youssef.task.business.entities

import com.google.gson.annotations.SerializedName

class Response<T>(@SerializedName("results") val results: T)