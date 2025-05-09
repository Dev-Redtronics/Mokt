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

package dev.redtronics.mokt.network

import io.ktor.http.*

public operator fun Url.div(path: String): Url {
    require(path.startsWith("/") && !path.endsWith("/")) { "Invalid path: $path" }
    return Url(this.toString() + path)
}
