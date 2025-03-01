/*
 * MIT License
 * Copyright 2024 Nils Jäkel & David Ernst
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the “Software”),
 * to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software.
 */

package dev.redtronics.mokt

import dev.redtronics.mokt.cinterop.get_env
import kotlinx.cinterop.toKString
import kotlinx.cinterop.ExperimentalForeignApi

@OptIn(ExperimentalForeignApi::class)
public actual fun getEnv(key: String): String? {
    val value = get_env(key)?.toKString()
    if (value.isNullOrEmpty()) {
        return null
    }
    return value
}

public actual val os: OsType = OsType.TVOS
