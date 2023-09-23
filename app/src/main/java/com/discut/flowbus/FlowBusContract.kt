package com.discut.flowbus

interface BusEvent

data class MainScreenToastEvent(val msg: String) : BusEvent