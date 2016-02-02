/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.auto.core.evernote;

import static com.auto.core.GlobalUtil.printErr;
import com.evernote.clients.NoteStoreClient;
import com.evernote.clients.UserStoreClient;
import com.evernote.edam.error.EDAMSystemException;
import com.evernote.edam.error.EDAMUserException;
import com.evernote.edam.type.Note;
import com.evernote.edam.type.Notebook;
import com.evernote.thrift.TException;

/**
 *
 * @author ethachu19
 */
public class EvernoteUser {

    public UserStoreClient userStore;
    public NoteStoreClient noteStore;
    public Notebook defaultNotebook;

    private EvernoteUser() {
    }

    EvernoteUser(UserStoreClient userStore, NoteStoreClient noteStore, String name) throws TException, EDAMUserException, EDAMSystemException {
        this.userStore = userStore;
        boolean versionOk = userStore.checkVersion("AutoFile",
                com.evernote.edam.userstore.Constants.EDAM_VERSION_MAJOR,
                com.evernote.edam.userstore.Constants.EDAM_VERSION_MINOR);
        if (!versionOk) {
            System.err.println("Incompatible Evernote client protocol version");
            System.exit(1);
        }
        this.noteStore = noteStore;
        if (name == null) name = "AutoFiler Default Notebook";
        for(Notebook nb : noteStore.listNotebooks()) {
            if(nb.getName().equals(name)) {
                defaultNotebook = nb;
                return;
            }
        }
        defaultNotebook = new Notebook();
        defaultNotebook.setName("AutoFiler Default Notebook");
        printErr(name + " is not a viable notebook");
        noteStore.createNotebook(defaultNotebook);
    }

    public void addNote(Note note) throws Exception {
        note.setNotebookGuid(defaultNotebook.getGuid());
        noteStore.createNote(note);
    }
}
