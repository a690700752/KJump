# KJump

A simplify plugin ported from vim-EasyMotion plugin for Intellij Platform IDE. And can be integrated with IdeaVim.

There are no default activated shortcuts. You can assign KJump activation shortcuts in `Settings->Keymap->KJump` menu, such as `<c-,>` `<c-;>` etc, or integrate with IdeaVim by add below section in ~/.ideavimrc:

```vimrc
nmap <leader><leader>s :action KJumpAction
nmap <leader><leader>w :action KJumpAction.Word0
nmap <leader><leader>l :action KJumpAction.Line
```

| Name         | Action            | Desc                                             |
|--------------|-------------------|--------------------------------------------------|
| KJump        | KJumpAction       | Input 1 character and jump to any same character |
| KJump Word 0 | KJumpAction.Word0 | Jump to any word.                                |
| KJump Line   | KJumpAction Line  | Jump to any line.                                |
