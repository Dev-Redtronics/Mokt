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

import dev.redtronics.buildsrc.Project

plugins {
    `maven-publish`
}

publishing {
    publications {
        withType<MavenPublication> {
            pom {
                name = Project.NAME
                description = Project.DESCRIPTION
                url = Project.URL
                inceptionYear = Project.INCEPTION_YEAR.toString()

                licenses {
                    license {
                        name = "MIT"
                        url = "https://opensource.org/licenses/MIT"
                    }
                }

                developers {
                    developer {
                        id = "redtronics"
                        name = "Nils Jaekel"
                        timezone = "Europe/Berlin"
                        email = "nils.jaekel@redtronics.dev"
                    }

                    developer {
                        id = "seri"
                        name = "David Ernst"
                        timezone = "Europe/Berlin"
                        email = "david.ernst@davils.net"
                    }
                }

                contributors {
                    contributor {
                        name = "Lars Ultsch"
                        timezone = "Europe/Berlin"
                    }
                }

                ciManagement {
                    system = Project.CI_NAME
                    url = Project.CI_URL
                }

                issueManagement {
                    system = Project.ISSUE_TRACKER
                    url = Project.ISSUE_TRACKER_URL
                }

                scm {
                    name = Project.SCM_NAME
                    url = Project.SCM_URL
                }
            }
        }
    }
}
