# KJump
<!-- Plugin description -->

A simplify plugin ported from vim-EasyMotion plugin for Intellij Platform IDE. Can be integrated with IdeaVim.
Support character, word, line jump.

## Usage
There are no default activated shortcuts. You can assign KJump activation shortcuts in `Settings->Keymap->KJump` menu, such as `<c-,>` `<c-;>` etc, or integrate with IdeaVim by add below section in ~/.ideavimrc:

```vimrc
nmap <leader><leader>s :action KJumpAction<cr>
nmap <leader><leader>w :action KJumpAction.Word0<cr>
nmap <leader><leader>l :action KJumpAction.Line<cr>
// more action see table below
```

| Name         | Action            | Desc                                                              |
|--------------|-------------------|-------------------------------------------------------------------|
| KJump        | KJumpAction       | Input 1 character and jump to any same character.                 |
| KJump Char 2 | KJumpAction.Char2 | Input 2 character and jump to any same character.                 |
| KJump Word 0 | KJumpAction.Word0 | Jump to any word.                                                 |
| KJump Word 1 | KJumpAction.Word1 | Input 1 character and jump to any word start with this character. |
| KJump Line   | KJumpAction.Line  | Jump to any line.                                                 |

<!-- Plugin description end -->
