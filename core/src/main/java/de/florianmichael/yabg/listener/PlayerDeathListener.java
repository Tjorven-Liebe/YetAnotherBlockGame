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

package de.florianmichael.yabg.listener;

import de.florianmichael.yabg.BukkitPlugin;
import de.florianmichael.yabg.island.YABGIsland;
import net.kyori.adventure.text.Component;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public final class PlayerDeathListener extends IslandListenerBase {

    public PlayerDeathListener(BukkitPlugin instance) {
        super(instance);
    }

    @EventHandler
    public void onPlayerDeath(final PlayerDeathEvent e) {
        if (e.getPlayer().getWorld() == instance.world()) {
            e.deathMessage(Component.empty());
        }
    }

    @EventHandler
    public void onPlayerRespawn(final PlayerRespawnEvent e) {
        final YABGIsland island = getIsland(e.getPlayer());
        if (island != null) {
            e.setRespawnLocation(island.getBlockLocation().add(0.5, 1, 0.5));
        }
    }

}
