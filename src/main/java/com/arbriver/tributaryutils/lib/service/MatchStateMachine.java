package com.arbriver.tributaryutils.lib.service;

import com.arbriver.tributaryutils.lib.model.MatchUpdate;
import org.springframework.stereotype.Service;

@Service
public class MatchStateMachine {
    public void processUpdate(MatchUpdate update) {
        //for now we are just saving the update to mongodb.
        //in the future we will check to see if it is a genuine update and we will send to stream

    }
}
