/*
 * MIT License
 * Copyright 2024 Nils Jäkel & David Ernst
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the "Software”),
 * to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software.
 */

package dev.redtronics.buildsrc.utils

/**
 * Enum class representing different operating system types.
 * This enumeration provides a type-safe way to identify and work with different
 * operating systems in the build process, allowing for platform-specific behavior
 * where needed.
 *
 * @property os The string identifier for the operating system
 * @since 0.1.0
 */
internal enum class OsType(val os: String) {
    WINDOWS("windows"),
    LINUX("linux"),
    MACOS("macos"),
    UNKNOWN("unknown");

    companion object {
        fun byName(name: String): OsType {
            return values().find { it.os == name } ?: UNKNOWN
        }
    }
}

/**
 * Lazily determined operating system of the current machine.
 * This property detects the current operating system by examining system properties
 * and returns the appropriate [OsType]. The detection is performed only once when
 * the property is first accessed, thanks to the lazy initialization.
 *
 * The detection logic checks for common substrings in the "os.name" system property
 * to identify Windows, Linux, macOS, or unknown operating systems.
 *
 * @since 0.1.0
 *
 * @see OsType
 * @see lazy
 */
internal val os: OsType by lazy {
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
