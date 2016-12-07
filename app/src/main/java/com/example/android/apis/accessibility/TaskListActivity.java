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

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.android.apis.R;

/** Starts up the task list that will interact with the AccessibilityService sample. */
public class TaskListActivity extends ListActivity {

    /** An intent for launching the system settings. */
    private static final Intent sSettingsIntent =
        new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tasklist_main);

        // Hard-coded hand-waving here.
        boolean[] checkboxes = {true, true, false, true, false, false, false};
        String[] labels = {"Take out Trash", "Do Laundry",
                           "Conquer World", "Nap", "Do Taxes",
                           "Abolish IRS", "Tea with Aunt Sharon" };

        TaskAdapter myAdapter = new TaskAdapter(this, labels, checkboxes);
        this.setListAdapter(myAdapter);

        // Add a shortcut to the accessibility settings.
        ImageButton button = (ImageButton) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(sSettingsIntent);
            }
        });
    }


    /**
     * Adds Accessibility information to individual child views of rows in the list.
     */
    public final class TaskAdapter extends BaseAdapter {

        private String[] mLabels = null;
        private boolean[] mCheckboxes = null;
        private Context mContext = null;

        public TaskAdapter(Context context, String[] labels, boolean[] checkboxes) {
            super();
            mContext = context;
            mLabels = labels;
            mCheckboxes = checkboxes;
        }

        @Override
        public int getCount() {
            return mLabels.length;
        }

        /**
         * Expands the views for individual list entries, and sets content descriptions for use by the
         * TaskBackAccessibilityService.
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(mContext);
                convertView = inflater.inflate(R.layout.tasklist_row, parent, false);
            }

            CheckBox checkbox = (CheckBox) convertView.findViewById(R.id.tasklist_finished);
            checkbox.setChecked(mCheckboxes[position]);

            TextView label = (TextView) (convertView.findViewById(R.id.tasklist_label));
            label.setText(mLabels[position]);

            String contentDescription = new StringBuilder()
                    .append(mContext.getString(R.string.task_name))
                    .append(' ')
                    .append(mLabels[position]).toString();
            label.setContentDescription(contentDescription);

            convertView.setTag(position);

            return convertView;
        }

        @Override
        public Object getItem(int position) {
            return mLabels[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }
    }
}
