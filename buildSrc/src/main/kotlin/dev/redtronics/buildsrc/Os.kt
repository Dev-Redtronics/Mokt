/*
 * MIT License
 * Copyright 2024 Nils Jäkel  & David Ernst
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the "Software”),
 * to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software.
 */

package dev.redtronics.buildsrc

enum class OsType(val value: String) {
    WINDOWS("windows"),
    LINUX("linux"),
    MACOS("macos"),
    UNKNOWN("unknown");
}

val os: OsType by lazy {
    val osName = System.getProperty("os.name").lowercase()
    when {
        osName.contains(other = "win") -> OsType.WINDOWS
        osName.contains(other = "nix") || osName.contains(other = "nux") || osName.contains(other = "aix") -> OsType.LINUX
        osName.contains(other = "mac") || osName.contains(other = "darwin") || osName.contains(other = "sunos") -> OsType.MACOS
        else -> {
            OsType.UNKNOWN
        }
    }
}