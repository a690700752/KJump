package com.werfad;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.util.TextRange;
import com.werfad.utils.EditorUtils;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KJumpAction extends AnAction {
    private JComponent mContentComponent;
    private KeyListener[] mOldListeners;
    private Editor mEditor;

    @Override
    public void update(AnActionEvent e) {
        Editor editor = e.getData(CommonDataKeys.EDITOR);
        e.getPresentation().setEnabled(editor != null);
    }

    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        mEditor = anActionEvent.getRequiredData(CommonDataKeys.EDITOR);
        mContentComponent = mEditor.getContentComponent();

        removeAllKeyListeners();
        mContentComponent.addKeyListener(mKeyListener);
    }

    private KeyAdapter mKeyListener = new KeyAdapter() {
        @Override
        public void keyTyped(KeyEvent e) {
            e.consume();
            char keyChar = e.getKeyChar();

            int[] borderOffset = EditorUtils.getVisibleBorderOffset(mEditor);
            String text = mEditor.getDocument().getText(new TextRange(borderOffset[0], borderOffset[1]));

            Messages.showDialog("Hello:" + keyChar + "\n" + text, "Selected Element:", new String[]{"OK"},
                    -1, null);

            mContentComponent.removeKeyListener(this);
            restoreKeyListeners();
        }
    };

    private void removeAllKeyListeners() {
        mOldListeners = mContentComponent.getKeyListeners();
        for (KeyListener mOldListener : mOldListeners) {
            mContentComponent.removeKeyListener(mOldListener);
        }
    }

    private void restoreKeyListeners() {
        for (KeyListener mOldListener : mOldListeners) {
            mContentComponent.addKeyListener(mOldListener);
        }
    }
}
