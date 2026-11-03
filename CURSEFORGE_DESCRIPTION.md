> ⚠️ **This project is AI-generated.** The code, this description and the icon of this mod were created with generative AI (Claude by Anthropic), directed by the author. Please keep that in mind, and report any problems on GitHub.

# Item Repair

Items no longer disappear when they run out of durability. Like the elytra, they stay in your inventory as a **broken** item that cannot be used until it is repaired.

## Features

- **Nothing breaks away** – when a tool, weapon or armor piece reaches 0 durability, the break sound plays but the item stays. A red line through its icon, the empty durability bar and a red "Broken" line in the tooltip mark it.
- **Broken items are useless** – they behave like an empty hand:
  - tools mine at hand speed and blocks only drop what they would drop for an empty hand,
  - weapons deal fist damage and armor gives no armor points,
  - enchantments have no effect,
  - right-clicking does nothing, while blocks react like to an empty hand (chests still open).
  - Broken armor and elytras can still be worn.
- **Repair as usual** – the anvil, the grindstone, combining two items in the crafting grid and Mending all work on broken items. Mending keeps working while broken, all other enchantments come back once the item is repaired.

No new items or textures are added. Only items of players are kept: equipment of mobs and tools used by dispensers still break like in vanilla.

## Installation

Install the mod on the **server and the client**. The server disconnects players whose client does not have the mod, with a message asking them to install it. On Fabric, [Fabric API](https://www.curseforge.com/minecraft/mc-mods/fabric-api) is required.

## Configuration

`config/itemrepair.properties` is created on first start and reloaded automatically when it changes. It can also be edited in game: on NeoForge in *Mods → Item Repair → Config*, on Fabric through [Mod Menu](https://www.curseforge.com/minecraft/mc-mods/modmenu). Invalid values are marked red and cannot be saved, and every value can be reset to its default.

The mod can be switched off, and single items can be excluded so they break like in vanilla.

## Versions

Available for **Fabric** and **NeoForge** on Minecraft **1.21 – 26.3**. Download the file that matches your loader and Minecraft version.

## Links

- [Source code](https://github.com/welles/minecraft-item-repair)
- [Report a problem](https://github.com/welles/minecraft-item-repair/issues)
- [Changelog](https://github.com/welles/minecraft-item-repair/blob/main/CHANGELOG.md)
