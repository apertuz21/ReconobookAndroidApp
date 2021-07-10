/*

MIT License

Copyright (c) 2021 TensorFlow

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation
files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use,
copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons
to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

* */




package com.uca.reconobookapp.utils;

import android.graphics.Bitmap;
import android.text.TextUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Size implements Comparable<Size>, Serializable {
    public static final long serialVersionUID = 7689808733290872361L;

    public final int width;
    public final int height;

    public Size(final int width, final int height) {
        this.width = width;
        this.height = height;
    }

    public Size(final Bitmap bmp) {
        this.width = bmp.getWidth();
        this.height = bmp.getHeight();
    }

    public static Size getRotatedSize(final Size size, final int rotation) {
        if (rotation % 180 != 0) {
            // The phone is portrait, therefore the camera is sideways and frame should be rotated.
            return new Size(size.height, size.width);
        }
        return size;
    }

    public static Size parseFromString(String sizeString) {
        if (TextUtils.isEmpty(sizeString)) {
            return null;
        }

        sizeString = sizeString.trim();

        // The expected format is "<width>x<height>".
        final String[] components = sizeString.split("x");
        if (components.length == 2) {
            try {
                final int width = Integer.parseInt(components[0]);
                final int height = Integer.parseInt(components[1]);
                return new Size(width, height);
            } catch (final NumberFormatException e) {
                return null;
            }
        } else {
            return null;
        }
    }

    public static List<Size> sizeStringToList(final String sizes) {
        final List<Size> sizeList = new ArrayList<Size>();
        if (sizes != null) {
            final String[] pairs = sizes.split(",");
            for (final String pair : pairs) {
                final Size size = Size.parseFromString(pair);
                if (size != null) {
                    sizeList.add(size);
                }
            }
        }
        return sizeList;
    }

    public static String sizeListToString(final List<Size> sizes) {
        String sizesString = "";
        if (sizes != null && sizes.size() > 0) {
            sizesString = sizes.get(0).toString();
            for (int i = 1; i < sizes.size(); i++) {
                sizesString += "," + sizes.get(i).toString();
            }
        }
        return sizesString;
    }

    public static final String dimensionsAsString(final int width, final int height) {
        return width + "x" + height;
    }

    public final float aspectRatio() {
        return (float) width / (float) height;
    }

    @Override
    public int compareTo(final Size other) {
        return width * height - other.width * other.height;
    }

    @Override
    public boolean equals(final Object other) {
        if (other == null) {
            return false;
        }

        if (!(other instanceof Size)) {
            return false;
        }

        final Size otherSize = (Size) other;
        return (width == otherSize.width && height == otherSize.height);
    }

    @Override
    public int hashCode() {
        return width * 32713 + height;
    }

    @Override
    public String toString() {
        return dimensionsAsString(width, height);
    }
}
