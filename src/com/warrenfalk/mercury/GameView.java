/*
 * Copyright (C) 2012 OUYA, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.warrenfalk.mercury;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

public class GameView extends GLSurfaceView {
    private Game game;

    public GameView(Game game, Context context, AttributeSet attrs) {
        super(context, attrs);
        this.game = game;
        init();
    }

    public GameView(Game game, Context context) {
        super(context);
        this.game = game;
        init();
    }

    private void init() {
    	setEGLConfigChooser(8, 8, 8, 8, 16, 0);
        setRenderer(game);
    }
}
