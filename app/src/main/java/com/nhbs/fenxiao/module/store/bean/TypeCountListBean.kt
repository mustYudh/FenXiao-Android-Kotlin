package com.nhbs.fenxiao.module.store.bean

import java.io.Serializable

/**
 * @author yudneghao
 * @date 2019-09-15
 */
data class TypeCountListBean(
    var classListTOS: List<ClassTOS>
): Serializable

data class ClassTOS(
    var classify: String,
    var id: String,
    var total: Int,
    var isChecked: Boolean = false
): Serializable