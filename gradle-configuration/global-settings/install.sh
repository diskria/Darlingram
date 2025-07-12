#!/bin/bash

find_project_root() {
  dir="$(pwd)"
  while [[ "$dir" != "/" ]]; do
    if [[ -f "$dir/settings.gradle.kts" || -f "$dir/settings.gradle" || -d "$dir/.git" ]]; then
      echo "$dir"
      return
    fi
    dir="$(dirname "$dir")"
  done
  return 1
}

PROJECT_ROOT="$(find_project_root)"
if [[ -z "$PROJECT_ROOT" ]]; then
  echo "❌ Project root not found (.git or settings.gradle(.kts) missing)"
  exit 1
fi

SETTINGS_FILE_REL="gradle-configuration/global-settings/global.settings.gradle.kts"
BUILD_SRC_FILE_REL="gradle-configuration/global-settings/global.build.src.gradle.kts"

SETTINGS_TARGET_FILE="$PROJECT_ROOT/$SETTINGS_FILE_REL"
BUILD_SRC_TARGET_FILE="$PROJECT_ROOT/$BUILD_SRC_FILE_REL"

SETTINGS_LINK_NAME="global.settings.gradle.kts"
BUILD_SRC_LINK_NAME="global.build.src.gradle.kts"

find "$PROJECT_ROOT" -type d \( \
  ! -path "*/build/*" \
  ! -path "*/.git/*" \
  ! -path "*/.gradle/*" \
  ! -path "*/telegram-clients/*" \
\) -print0 | while IFS= read -r -d '' dir; do
  if [[ -f "$dir/settings.gradle.kts" || -f "$dir/settings.gradle" ]]; then
    LINK_PATH="$dir/$SETTINGS_LINK_NAME"
    RELATIVE_TARGET=$(realpath --relative-to="$dir" "$SETTINGS_TARGET_FILE")

    if [[ -L "$LINK_PATH" || -f "$LINK_PATH" ]]; then
      echo "♻️  Removing existing link or file: $LINK_PATH"
      rm "$LINK_PATH"
    fi

    echo "🔗 Creating symlink: $LINK_PATH → $RELATIVE_TARGET"
    ln -s "$RELATIVE_TARGET" "$LINK_PATH"

    if [[ "$(basename "$dir")" == "buildSrc" ]]; then
      BUILD_SRC_LINK_PATH="$dir/$BUILD_SRC_LINK_NAME"
      RELATIVE_BUILD_SRC_TARGET=$(realpath --relative-to="$dir" "$BUILD_SRC_TARGET_FILE")

      if [[ -L "$BUILD_SRC_LINK_PATH" || -f "$BUILD_SRC_LINK_PATH" ]]; then
        echo "♻️  Removing existing link or file: $BUILD_SRC_LINK_PATH"
        rm "$BUILD_SRC_LINK_PATH"
      fi

      echo "🔗 Creating symlink: $BUILD_SRC_LINK_PATH → $RELATIVE_BUILD_SRC_TARGET"
      ln -s "$RELATIVE_BUILD_SRC_TARGET" "$BUILD_SRC_LINK_PATH"
    fi
  fi
done
