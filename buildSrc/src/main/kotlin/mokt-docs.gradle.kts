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

import dev.redtronics.buildsrc.Project
import java.time.Year

plugins {
    org.jetbrains.dokka
}

public val projectModuleName: String = project.name.replaceFirstChar { it.uppercase() }
dokka {
    moduleName.set(projectModuleName)

    dokkaPublications.html {
        includes.from("README.md")
    }

    pluginsConfiguration.html {
        footerMessage.set("Copyright © ${Year.now().value} Nils Jäkel & David Ernst")
    }
}
