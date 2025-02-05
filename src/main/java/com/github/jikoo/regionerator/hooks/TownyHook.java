/*
 * Copyright (c) 2015-2021 by Jikoo.
 *
 * Regionerator is licensed under a Creative Commons
 * Attribution-ShareAlike 4.0 International License.
 *
 * You should have received a copy of the license along with this
 * work. If not, see <http://creativecommons.org/licenses/by-sa/4.0/>.
 */

package com.github.jikoo.regionerator.hooks;

import com.github.jikoo.planarwrappers.util.Coords;
import com.palmergames.bukkit.towny.TownySettings;
import com.palmergames.bukkit.towny.object.Coord;
import com.palmergames.bukkit.towny.object.WorldCoord;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;

/**
 * PluginHook for <a href="https://github.com/LlmDl/Towny">Towny</a>.
 */
public class TownyHook extends PluginHook {

	public TownyHook() {
		super("Towny");
	}

	@Override
	public boolean isChunkProtected(@NotNull World chunkWorld, int chunkX, int chunkZ) {
		int minX = Coords.chunkToBlock(chunkX);
		int maxX = minX + 15;
		int minZ = Coords.chunkToBlock(chunkZ);
		int maxZ = minZ + 15;

		Coord lowCoord = Coord.parseCoord(minX, minZ);
		Coord highCoord = Coord.parseCoord(maxX, maxZ);
		int cellSize = TownySettings.getTownBlockSize();

		for (int x = lowCoord.getX(); x <= highCoord.getX(); x += cellSize) {
			for (int z = lowCoord.getZ(); z <= highCoord.getZ(); z += cellSize) {
				WorldCoord worldCoord = new WorldCoord(chunkWorld.getName(), x, z);
				try {
					if (worldCoord.getTownBlock().hasTown()) {
						return true;
					}
				} catch (Exception ignored) {}
			}
		}
		return false;
	}

}
