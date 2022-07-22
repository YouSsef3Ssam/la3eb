package com.youssef.task.business.entities.errors

import com.google.gson.annotations.SerializedName

data class ServerError(@SerializedName("error") val error: String?)