/*
 * MIT License
 * Copyright 2024 Nils Jäkel
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the “Software”),
 * to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software.
 */

package dev.redtronics.mokt.provider

import io.ktor.http.*

public class Authentik : Provider {
    override val name: String
        get() = "Authentik"
    override var url: Url
        get() = TODO("Not yet implemented")
        set(value) {}
    override val host: String
        get() = TODO("Not yet implemented")
    override val port: Int
        get() = TODO("Not yet implemented")
}
