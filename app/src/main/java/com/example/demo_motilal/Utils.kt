package com.example.demo_motilal

import java.text.SimpleDateFormat

fun String.getFormattedDate(): String? {
    val strAr = this.split("T")
    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    val parsedDate = sdf.parse(strAr[0]+" "+strAr[1])
    val sdf1 = SimpleDateFormat("dd MMM yyyy")
    return sdf1.format(parsedDate)
}
