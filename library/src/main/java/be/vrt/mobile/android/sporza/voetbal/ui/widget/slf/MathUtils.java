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

/**
 * Helper class to perform math computations.
 */
public final class MathUtils {

    public static final float FLOAT_DIFFERENTIATOR = 0.01f;
    public static final int RECT_TRUNCATE_PLACES = 3;
    public static final int BASE_10 = 10;

    private MathUtils() {
    }

    /**
     * Truncates a float number {@code f} to {@code decimalPlaces}.
     * @param f the number to be truncated.
     * @param decimalPlaces the amount of decimals that {@code f}
     * will be truncated to.
     * @return a truncated representation of {@code f}.
     */
    protected static float truncate(final float f, final int decimalPlaces) {
        float decimalShift = (float) Math.pow(BASE_10, decimalPlaces);
        return Math.round(f * decimalShift) / decimalShift;
    }

    /**
     * Checks whether two {@link RectF} have the same aspect ratio.
     * @param r1 the first rect.
     * @param r2  the second rect.
     * @return {@code true} if both rectangles have the same aspect ratio,
     * {@code false} otherwise.
     */
    protected static boolean haveSameAspectRatio(final RectF r1, final RectF r2) {
        // Reduces precision to avoid problems when comparing aspect ratios.
        float srcRectRatio = MathUtils.truncate(MathUtils.getRectRatio(r1), RECT_TRUNCATE_PLACES);
        float dstRectRatio = MathUtils.truncate(MathUtils.getRectRatio(r2), RECT_TRUNCATE_PLACES);

        // Compares aspect ratios that allows for a tolerance range of [0, 0.01]
        return (Math.abs(srcRectRatio - dstRectRatio) <= FLOAT_DIFFERENTIATOR);
    }

    /**
     * Computes the aspect ratio of a given rect.
     * @param rect the rect to have its aspect ratio computed.
     * @return the rect aspect ratio.
     */
    protected static float getRectRatio(final RectF rect) {
        return rect.width() / rect.height();
    }
}
