/*
 * Copyright 2014 Flavio Faria
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
package be.vrt.mobile.android.sporza.voetbal.ui.widget.slf;

import android.graphics.RectF;

public interface TransitionGenerator {

    /**
     * Generates the next transition to be played by the {@link PanningImageView}.
     * @param drawableBounds the bounds of the drawable to be shown in the {@link PanningImageView}.
     * @param viewport the rect that represents the viewport where
     *                 the transition will be played in. This is usually the bounds of the
     *                 {@link PanningImageView}.
     * @return a {@link Transition} object to be played by the {@link PanningImageView}.
     */
    Transition generateNextTransition(final RectF drawableBounds, final RectF viewport);

}
