/*
 * This file is part of YetAnotherBlockGame - https://github.com/FlorianMichael/YetAnotherBlockGame
 * Copyright (C) 2024 FlorianMichael/EnZaXD <florian.michael07@gmail.com> and contributors
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.florianmichael.yabg.config;

import de.florianmichael.yabg.island.IslandTracker;
import de.florianmichael.yabg.island.YABGIsland;
import de.florianmichael.yabg.util.wrapper.WrappedConfig;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class IslandsSave extends WrappedConfig {

    private final IslandTracker tracker;

    public IslandsSave(final IslandTracker tracker) {
        this.tracker = tracker;
    }

    @Override
    public void read() {
        for (ConfigurationSection group : groups()) {
            final int chunkX = group.getInt("chunk-x");
            final int chunkY = group.getInt("chunk-y");
            final UUID owner = UUID.fromString(group.getName());
            final String name = group.isString("name") ? group.getString("name") : null;
            final List<UUID> members = group.isList("members") ? group.getStringList("members").stream().map(UUID::fromString).toList() : new ArrayList<>();
            tracker.islands().add(new YABGIsland(owner, chunkX, chunkY, name, members));
        }
    }

    @Override
    public void write() {
        for (YABGIsland island : tracker.islands()) {
            createGroup(island.owner().toString(), section -> {
                section.set("chunk-x", island.chunkX());
                section.set("chunk-y", island.chunkY());
                if (island.name() != null) {
                    section.set("name", island.name());
                }
                if (!island.members().isEmpty()) {
                    section.set("members", island.members().stream().map(UUID::toString).toList());
                }
            });
        }
    }
}