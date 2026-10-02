# Lookout Awning — Forge 1.20.1 source project

A survival-craftable, repeatable shallow canvas awning for Tate's tree lookout. Each piece takes one block of horizontal space, faces four directions and has a sloped 3D cloth surface with acacia timber edges. Place several beside each other for a broad canopy, with the high side against the tree. It uses vanilla white wool and stripped acacia textures, so existing resource packs can restyle it. The shape is rigid and angular rather than simulated cloth.

## Recipe
Three white wool over three string over two acacia slabs (left and right) produces four awnings. The block drops itself.

## Build / install
Use Java 17. From this directory, with Gradle installed, run `gradle build` and copy `build/libs/lookout-awning-1.0.0.jar` to the Forge 1.20.1 mods folder. Alternatively import this project into a Forge MDK/Gradle environment. Build dependencies come from Forge Maven. This archive contains source and assets, **not a ready-to-install mod JAR**. The code has not been compiled or tested in Minecraft because this workspace has no Gradle installation or access to Forge Maven. Back up the world before trying a newly built mod.

## Next pass
The reference canopy has softer draped cloth and irregular scallops. A second end-piece or a custom baked mesh/texture can add that silhouette once the basic roof scale looks right in game.
