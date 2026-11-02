# Changelog

All notable changes to this mod are documented in this file. The format is based on
[Keep a Changelog](https://keepachangelog.com/en/1.1.0/), and the mod follows [Semantic Versioning](https://semver.org/).

## [1.2.0] - 2026-11-03

### Added

- A pixel art icon, shown in Mod Menu, in NeoForge's mod list and at the top of the README.

## [1.1.0] - 2026-11-02

### Added

- Every value in the config screen can be reset to its default with the ↺ button next to it, or all at once with
  Reset all. Nothing is saved until Done.

## [1.0.0] - 2026-10-26

First release, for Fabric and NeoForge on Minecraft 1.21.1 – 26.3.

### Added

- Tools, weapons and armor of players no longer disappear at 0 durability: like the elytra, they stay in the
  inventory as a broken item until they are repaired.
- Broken items are useless: tools mine at hand speed, weapons deal fist damage, armor gives no armor points,
  enchantments except Mending have no effect and right-clicking does nothing.
- Broken items are struck through in inventories, have an empty durability bar and a red "Broken" line in the tooltip.
- Items can be excluded so they break like in vanilla.
- The server disconnects players without the client mod, with a message asking them to install it.
- `config/itemrepair.properties`, reloaded automatically and editable in game through NeoForge's mod list or
  Mod Menu.

[1.2.0]: https://github.com/welles/minecraft-item-repair/compare/v1.1.0...v1.2.0
[1.1.0]: https://github.com/welles/minecraft-item-repair/compare/v1.0.0...v1.1.0
[1.0.0]: https://github.com/welles/minecraft-item-repair/releases/tag/v1.0.0
