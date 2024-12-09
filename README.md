# KJump

[![Build](https://github.com/a690700752/KJump/workflows/Build/badge.svg)](https://github.com/a690700752/KJump/actions/workflows/build.yml)
[![Version](https://img.shields.io/jetbrains/plugin/v/15097-kjump.svg)](https://plugins.jetbrains.com/plugin/15097-kjump)
[![Downloads](https://img.shields.io/jetbrains/plugin/d/15097-kjump.svg)](https://plugins.jetbrains.com/plugin/15097-kjump)

<!-- Plugin description -->
A powerful navigation plugin ported from vim-EasyMotion for JetBrains IDEs. KJump enhances your code navigation experience by providing quick jump capabilities to characters, words, and lines. It seamlessly integrates with IdeaVim for a more natural Vim-like experience.

## Features

- Quick character jump: Jump to any character in the visible area
- Word navigation: Jump to any word or word starting with a specific character
- Line jumping: Quickly navigate to any line
- IdeaVim integration: Works seamlessly with IdeaVim
- Minimal keystrokes: Reach your target with just a few keystrokes

## Installation

1. Open your JetBrains IDE
2. Go to `Settings/Preferences → Plugins`
3. Click on `Marketplace`
4. Search for "KJump"
5. Click `Install`

## Usage

### Keyboard Shortcuts
There are no default activated shortcuts. You can assign KJump activation shortcuts in:
`Settings → Keymap → KJump`

Common shortcut suggestions:
- `Ctrl+,` for character jump
- `Ctrl+;` for word jump

### IdeaVim Integration
Add the following commands to your `~/.ideavimrc`:

```vimrc
" Basic jumps
nmap <leader><leader>s :action KJumpAction<cr>
nmap <leader><leader>w :action KJumpAction.Word0<cr>
nmap <leader><leader>l :action KJumpAction.Line<cr>

" Additional jumps
nmap <leader><leader>c :action KJumpAction.Char2<cr>
nmap <leader><leader>f :action KJumpAction.Word1<cr>
```

### Available Actions

| Name                | Action                  | Description                                                         |
|---------------------|-------------------------|---------------------------------------------------------------------|
| KJump               | KJumpAction             | Input 1 character and jump to any same character                    |
| KJump Char 2        | KJumpAction.Char2       | Input 2 characters and jump to any matching position                |
| KJump Word 0        | KJumpAction.Word0       | Jump to any word                                                    |
| KJump Word 1        | KJumpAction.Word1       | Input 1 character and jump to any word starting with this character |
| KJump Line          | KJumpAction.Line        | Jump to any line                                                    |
| KJump Global Line   | KJumpAction.GlobalLine  | Jump to any line across all visible editors                         |
| KJump Global Word 0 | KJumpAction.GlobalWord0 | Jump to any word across all visible editors                         |

<!-- Plugin description end -->

## Tips
- Use Word0 for general word navigation
- Use Char2 for more precise jumps
- Line mode is great for long files
- Combine with IdeaVim for the best experience

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Acknowledgments

- Inspired by [vim-EasyMotion](https://github.com/easymotion/vim-easymotion)
- Thanks to all contributors who have helped this project
