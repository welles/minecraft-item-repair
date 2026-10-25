# Item Repair

A Minecraft mod for **Fabric** and **NeoForge** (Minecraft 1.21.1 – 26.3) that stops items from disappearing when they
run out of durability. Like the elytra, they stay in the inventory as a **broken** item that cannot be used until it is
repaired.

- **Nothing breaks away** – when a player's tool, weapon or armor piece reaches 0 durability, the usual break sound
  plays, but the item stays. A red line through its icon in the hotbar and inventories, the empty durability bar
  and a red "Broken" line in the tooltip mark it as broken.
- **Broken items are useless** – they behave like an empty hand:
  - tools mine at hand speed and blocks only drop what they would drop for an empty hand,
  - weapons deal fist damage, armor gives no armor points,
  - enchantments have no effect (Protection, Sharpness, Fortune, Silk Touch, Looting, ...),
  - right-clicking does nothing (no bow, shield, fishing rod, flint and steel, hoe, ...), blocks react like to an empty
    hand (chests still open) and entities ignore the item (no shearing sheep, no brushing armadillos).
  - Broken armor and elytras can still be worn and equipped by right-clicking.
- **Repair as usual** – everything in vanilla that repairs items works on broken items as well: the anvil (with material
  or a second item), the grindstone, combining two items in the crafting grid and Mending (Mending keeps working on
  broken items, all other enchantments are restored once the item is repaired). Curses still apply.

No textures or new items are added – a broken item is the same item with 0 durability left.

## Installation

Install the mod on the **server and the client**. The client needs it to predict mining speed and right-click
behaviour correctly and to show the strike-through and the "Broken" tooltip. The server disconnects players whose
client does not have the mod, with a message asking them to install it. On Fabric, the mod requires
[Fabric API](https://modrinth.com/mod/fabric-api).

Only items of players are kept. Equipment of mobs, armor worn by wolves and tools used by dispensers still break and
disappear like in vanilla.

## Configuration

`config/itemrepair.properties` is created on first start. Changes are applied automatically while the game is running.

| Key             | Default | Description                                                                                                  |
|-----------------|---------|--------------------------------------------------------------------------------------------------------------|
| `enabled`       | `true`  | Master switch. `false` = items break and disappear like in vanilla. Already broken items stay broken.        |
| `excludedItems` | (empty) | Comma separated item ids that break and disappear like in vanilla, e.g. `minecraft:fishing_rod,minecraft:shears`. |

## Building

The project uses [Stonecutter](https://stonecutter.kikugie.dev/) to build every Minecraft version and loader from one
source tree. Each node in `settings.gradle.kts` targets one Minecraft version and declares the compatible range in
`stonecutter.properties.toml`.

```sh
./gradlew build                          # build all versions and loaders
./gradlew buildAndCollect                # copy all jars to build/libs/<mod version>/
./gradlew :1.21.1-fabric:runClient       # run a specific version (Fabric)
./gradlew :26.3-neoforge:runClient       # run a specific version (NeoForge)
```

Every push to `main` builds all versions on GitHub Actions; the jars can be downloaded from the run's
artifacts.

On **NixOS**, run the game inside the provided Nix shell so Minecraft finds its native libraries
(otherwise it crashes with `GLFW error during init: Failed to detect any supported platform`):

```sh
nix-shell --run './gradlew :1.21.1-fabric:runClient'
```

Version-specific code uses Stonecutter comments (`//? if >=1.21.2 {`). The source tree is checked in for the
`1.21.1-fabric` node; switch the active version with the `Set active project to …` Gradle tasks before editing code
for another version, and switch back to `1.21.1-fabric` (`Reset active project`) before committing.

## License

[MIT](LICENSE)
