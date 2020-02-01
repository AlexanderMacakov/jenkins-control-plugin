/*
 * Copyright (c) 2013 David Boissier
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.codinjutsu.tools.jenkins.model;

import org.apache.log4j.Logger;

/**
 * Currently missing color: nobuilt
 * @see <a href="https://github.com/jenkinsci/jenkins/blob/master/core/src/main/java/hudson/model/BallColor.java">Jenkins Color</a>
 * @see <a href="https://github.com/jenkinsci/jenkins/blob/master/core/src/main/java/hudson/model/StatusIcon.java>Jenkins Status</a>
 */
public enum BuildStatusEnum {

    FAILURE("Failure", "red"),
    UNSTABLE("Unstable", "yellow"),
    ABORTED("Aborted", "aborted"),
    SUCCESS("Success", "blue"),
    STABLE("Stable", "blue"),
    NULL("Null", "disabled"),
    // TODO: handle the folder-case explicitly. @mcmics: use better Folder Detection
    // instead of simply making it a BuildStatusEnum so that the icon renders
    FOLDER("Folder", "disabled");


    private static final Logger log = Logger.getLogger(BuildStatusEnum.class);

    private final String status;
    private final String color;


    BuildStatusEnum(String status, String color) {
        this.status = status;
        this.color = color;
    }

    public static BuildStatusEnum parseStatus(String status) {
        BuildStatusEnum buildStatusEnum;
        try {
            if (status == null || "null".equals(status)) {
                status = "NULL";
            }
            buildStatusEnum = valueOf(status.toUpperCase());

        } catch (IllegalArgumentException ex) {
            log.info("Unsupported status : " + status, ex);
            buildStatusEnum = NULL;
        }
        return buildStatusEnum;
    }

    /**
     * Parse status from color
     */
    public static BuildStatusEnum getStatus(String jobColor) {
        if (null == jobColor) {
            return NULL;
        }
        BuildStatusEnum[] jobStates = values();
        for (BuildStatusEnum jobStatus : jobStates) {
            String stateName = jobStatus.getColor();
            if (jobColor.startsWith(stateName)) {
                return jobStatus;
            }
        }

        return NULL;
    }


    public String getStatus() {
        return status;
    }


    public String getColor() {
        return color;
    }


}
