/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.example.android.apis.accessibility;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.apis.R;


/**
 * Acts as a go-between for all AccessibilityEvents sent from items in the ListView, providing the
 * option of sending more context to an AccessibilityService by adding more AccessiblityRecords to
 * an event.
 */
public class TaskListView extends ListView {

    public TaskListView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    /**
     * This method will fire whenever a child event wants to send an AccessibilityEvent.  As a
     * result, it's a great place to add more AccessibilityRecords, if you want.  In this case,
     * the code is grabbing the position of the item in the list, and assuming that to be the
     * priority for the task.
     */
    @Override
    public boolean onRequestSendAccessibilityEvent(View child, AccessibilityEvent event) {
        // Add a record for ourselves as well.
        AccessibilityEvent record = AccessibilityEvent.obtain();
        super.onInitializeAccessibilityEvent(record);

        int priority = (Integer) child.getTag();
        String priorityStr = "Priority: " + priority;
        record.setContentDescription(priorityStr);

        event.appendRecord(record);
        return true;
    }

}
