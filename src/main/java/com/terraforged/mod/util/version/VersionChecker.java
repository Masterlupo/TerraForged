/*
 * MIT License
 *
 * Copyright (c) 2020 TerraForged
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.terraforged.mod.util.version;

import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.moddiscovery.ModInfo;
import org.apache.maven.artifact.versioning.ArtifactVersion;

public class VersionChecker {

    public static void require(String modid, int major, int minor, int patch) {
        for (ModInfo mod : ModList.get().getMods()) {
            if (mod.getModId().equals(modid)) {
                ArtifactVersion version = mod.getVersion();
                if (version.getMajorVersion() > major) {
                    return;
                }
                if (version.getMajorVersion() == major && version.getMinorVersion() > minor) {
                    return;
                }
                if (version.getMajorVersion() == major && version.getMinorVersion() == minor && version.getIncrementalVersion() >= patch) {
                    return;
                }
                throw new VersionError(modid, major, minor, patch, toString(version));
            }
        }
    }

    public static String toString(ArtifactVersion version) {
        return version.getMajorVersion() + "." + version.getMinorVersion() + "." + version.getIncrementalVersion();
    }
}
