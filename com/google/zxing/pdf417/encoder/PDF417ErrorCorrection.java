package com.google.zxing.pdf417.encoder;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.core.app.FrameMetricsAggregator;
import androidx.core.view.InputDeviceCompat;
import com.alibaba.fastjson.asm.Opcodes;
import com.brgd.brblmesh.GeneralClass.DisDoubleClickListener;
import com.brgd.brblmesh.GeneralClass.GlobalBluetooth;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.error.GattError;
import com.google.zxing.WriterException;
import com.google.zxing.pdf417.PDF417Common;
import com.king.camera.scan.config.ResolutionCameraConfig;
import com.king.zxing.util.CodeUtils;

/* JADX INFO: loaded from: classes4.dex */
final class PDF417ErrorCorrection {
    private static final int[][] EC_COEFFICIENTS = {new int[]{27, 917}, new int[]{522, 568, 723, 809}, new int[]{237, 308, 436, 284, 646, 653, 428, 379}, new int[]{274, 562, 232, 755, 599, 524, 801, GattError.GATT_BUSY, 295, 116, 442, 428, 295, 42, Opcodes.ARETURN, 65}, new int[]{361, 575, 922, 525, Opcodes.ARETURN, 586, CodeUtils.DEFAULT_REQ_HEIGHT, 321, 536, 742, 677, 742, 687, 284, 193, 517, 273, 494, 263, 147, 593, 800, 571, 320, 803, GattError.GATT_ERROR, 231, 390, 685, 330, 63, 410}, new int[]{539, TypedValues.CycleType.TYPE_CUSTOM_WAVE_SHAPE, 6, 93, 862, 771, 453, 106, TypedValues.MotionType.TYPE_QUANTIZE_MOTIONSTEPS, 287, 107, TypedValues.PositionType.TYPE_SIZE_PERCENT, 733, 877, 381, TypedValues.MotionType.TYPE_QUANTIZE_INTERPOLATOR_ID, 723, 476, 462, 172, 430, TypedValues.MotionType.TYPE_POLAR_RELATIVETO, 858, 822, 543, 376, FrameMetricsAggregator.EVERY_DURATION, 400, 672, 762, 283, Opcodes.INVOKESTATIC, 440, 35, 519, 31, 460, 594, 225, 535, 517, 352, TypedValues.MotionType.TYPE_ANIMATE_RELATIVE_TO, Opcodes.IFLE, 651, 201, 488, TypedValues.PositionType.TYPE_DRAWPATH, 648, 733, 717, 83, 404, 97, 280, 771, 840, 629, 4, 381, 843, 623, 264, 543}, new int[]{521, 310, 864, 547, 858, 580, 296, 379, 53, 779, 897, 444, 400, 925, 749, 415, 822, 93, 217, 208, PDF417Common.MAX_CODEWORDS_IN_BARCODE, 244, 583, 620, 246, Opcodes.LCMP, 447, 631, 292, 908, 490, TypedValues.TransitionType.TYPE_AUTO_TRANSITION, 516, 258, 457, 907, 594, 723, 674, 292, 272, 96, 684, 432, 686, TypedValues.MotionType.TYPE_ANIMATE_CIRCLEANGLE_TO, 860, 569, 193, 219, GattError.GATT_INTERNAL_ERROR, 186, 236, 287, Opcodes.CHECKCAST, 775, 278, 173, 40, 379, 712, 463, 646, 776, 171, 491, 297, 763, 156, 732, 95, 270, 447, 90, TypedValues.PositionType.TYPE_PERCENT_Y, 48, 228, 821, 808, 898, 784, 663, 627, 378, 382, 262, 380, TypedValues.MotionType.TYPE_QUANTIZE_MOTION_PHASE, 754, 336, 89, 614, 87, 432, 670, 616, 157, 374, 242, 726, 600, 269, 375, 898, 845, 454, 354, GattError.GATT_WRONG_STATE, 814, 587, 804, 34, 211, 330, 539, 297, 827, 865, 37, 517, 834, 315, 550, 86, 801, 4, AppCompatDelegate.FEATURE_SUPPORT_ACTION_BAR, 539}, new int[]{524, 894, 75, 766, 882, 857, 74, 204, 82, 586, 708, 250, TypedValues.Custom.TYPE_DIMENSION, 786, GattError.GATT_MORE, ResolutionCameraConfig.IMAGE_QUALITY_720P, 858, 194, 311, 913, 275, 190, 375, 850, 438, 733, 194, 280, 201, 280, 828, 757, 710, 814, 919, 89, 68, 569, 11, 204, 796, TypedValues.MotionType.TYPE_ANIMATE_RELATIVE_TO, 540, 913, 801, TypedValues.TransitionType.TYPE_DURATION, 799, GattError.GATT_AUTH_FAIL, 439, 418, 592, 668, 353, 859, 370, 694, 325, 240, 216, 257, 284, 549, 209, 884, 315, 70, 329, 793, 490, 274, 877, Opcodes.IF_ICMPGE, 749, 812, 684, 461, 334, 376, 849, 521, 307, 291, 803, 712, 19, 358, 399, 908, 103, FrameMetricsAggregator.EVERY_DURATION, 51, 8, 517, 225, 289, 470, 637, 731, 66, 255, 917, 269, 463, 830, 730, 433, 848, 585, GattError.GATT_PENDING, 538, TypedValues.Custom.TYPE_REFERENCE, 90, 2, 290, 743, Opcodes.IFNONNULL, 655, TypedValues.Custom.TYPE_STRING, 329, 49, 802, 580, 355, 588, Opcodes.NEWARRAY, 462, 10, GattError.GATT_CMD_STARTED, 628, 320, 479, GattError.GATT_WRONG_STATE, 739, 71, 263, TypedValues.AttributesType.TYPE_PIVOT_TARGET, 374, 601, Opcodes.CHECKCAST, TypedValues.MotionType.TYPE_ANIMATE_RELATIVE_TO, GattError.GATT_NOT_ENCRYPTED, 673, 687, 234, 722, 384, Opcodes.RETURN, 752, TypedValues.MotionType.TYPE_PATHMOTION_ARC, CodeUtils.DEFAULT_REQ_HEIGHT, 455, 193, 689, TypedValues.TransitionType.TYPE_TRANSITION_FLAGS, 805, 641, 48, 60, 732, 621, 895, 544, 261, 852, 655, 309, 697, 755, 756, 60, 231, 773, 434, 421, 726, 528, TypedValues.PositionType.TYPE_PERCENT_WIDTH, 118, 49, 795, 32, 144, DisDoubleClickListener.MIN_CLICK_DELAY_TIME, 238, 836, 394, 280, 566, 319, 9, 647, 550, 73, 914, 342, 126, 32, 681, 331, 792, 620, 60, TypedValues.MotionType.TYPE_POLAR_RELATIVETO, 441, Opcodes.GETFIELD, 791, 893, 754, TypedValues.MotionType.TYPE_ANIMATE_RELATIVE_TO, 383, 228, 749, 760, 213, 54, 297, GattError.GATT_CMD_STARTED, 54, 834, 299, 922, 191, 910, 532, TypedValues.MotionType.TYPE_POLAR_RELATIVETO, 829, 189, 20, Opcodes.GOTO, 29, 872, 449, 83, TypedValues.CycleType.TYPE_VISIBILITY, 41, 656, TypedValues.PositionType.TYPE_SIZE_PERCENT, 579, 481, 173, 404, 251, 688, 95, 497, 555, 642, 543, 307, Opcodes.IF_ICMPEQ, 924, 558, 648, 55, 497, 10}, new int[]{352, 77, 373, TypedValues.PositionType.TYPE_PERCENT_HEIGHT, 35, 599, 428, 207, 409, 574, 118, 498, 285, 380, 350, 492, 197, 265, 920, 155, 914, 299, 229, 643, 294, 871, 306, 88, 87, 193, 352, 781, 846, 75, 327, 520, 435, 543, 203, 666, 249, 346, 781, 621, CodeUtils.DEFAULT_REQ_HEIGHT, 268, 794, 534, 539, 781, 408, 390, 644, 102, 476, 499, 290, 632, 545, 37, 858, 916, 552, 41, 542, 289, 122, 272, 383, 800, 485, 98, 752, 472, 761, 107, 784, 860, 658, 741, 290, 204, 681, 407, 855, 85, 99, 62, 482, Opcodes.GETFIELD, 20, 297, 451, 593, 913, GattError.GATT_NOT_ENCRYPTED, 808, 684, 287, 536, 561, 76, 653, 899, 729, 567, 744, 390, InputDeviceCompat.SOURCE_DPAD, Opcodes.CHECKCAST, 516, 258, 240, 518, 794, 395, 768, 848, 51, TypedValues.MotionType.TYPE_QUANTIZE_MOTIONSTEPS, 384, 168, 190, 826, 328, 596, 786, 303, 570, 381, 415, 641, 156, 237, Opcodes.DCMPL, 429, 531, 207, 676, 710, 89, 168, 304, TypedValues.CycleType.TYPE_VISIBILITY, 40, 708, 575, Opcodes.IF_ICMPGE, 864, 229, 65, 861, 841, 512, 164, 477, 221, 92, 358, 785, 288, 357, 850, 836, 827, 736, TypedValues.TransitionType.TYPE_TRANSITION_FLAGS, 94, 8, 494, 114, 521, 2, 499, 851, 543, 152, 729, 771, 95, 248, 361, 578, 323, 856, 797, 289, 51, 684, 466, 533, 820, 669, 45, TypedValues.Custom.TYPE_COLOR, 452, Opcodes.GOTO, 342, 244, 173, 35, 463, 651, 51, 699, 591, 452, 578, 37, 124, 298, 332, 552, 43, 427, 119, 662, 777, 475, 850, 764, 364, 578, 911, 283, 711, 472, TypedValues.CycleType.TYPE_EASING, 245, 288, 594, 394, FrameMetricsAggregator.EVERY_DURATION, 327, 589, 777, 699, 688, 43, 408, 842, 383, 721, 521, 560, 644, 714, 559, 62, 145, 873, 663, 713, Opcodes.IF_ICMPEQ, 672, 729, 624, 59, 193, 417, Opcodes.IFLE, 209, 563, 564, 343, 693, AppCompatDelegate.FEATURE_SUPPORT_ACTION_BAR_OVERLAY, TypedValues.MotionType.TYPE_DRAW_PATH, 563, 365, Opcodes.PUTFIELD, 772, 677, 310, 248, 353, 708, 410, 579, 870, 617, 841, 632, 860, 289, 536, 35, 777, 618, 586, TypedValues.CycleType.TYPE_WAVE_OFFSET, 833, 77, 597, 346, 269, 757, 632, 695, 751, 331, 247, Opcodes.INVOKESTATIC, 45, 787, 680, 18, 66, 407, 369, 54, 492, 228, 613, 830, 922, 437, 519, 644, TypedValues.Custom.TYPE_DIMENSION, 789, TypedValues.CycleType.TYPE_EASING, 305, 441, 207, 300, 892, 827, GattError.GATT_ENCRYPTED_NO_MITM, 537, 381, 662, InputDeviceCompat.SOURCE_DPAD, 56, GlobalBluetooth.MAX_FIXED_GROUP_ID, 341, 242, 797, 838, 837, ResolutionCameraConfig.IMAGE_QUALITY_720P, 224, 307, 631, 61, 87, 560, 310, 756, 665, 397, 808, 851, 309, 473, 795, 378, 31, 647, 915, 459, 806, 590, 731, TypedValues.CycleType.TYPE_WAVE_PHASE, 216, 548, 249, 321, 881, 699, 535, 673, 782, 210, 815, TypedValues.Custom.TYPE_DIMENSION, 303, 843, 922, 281, 73, 469, 791, 660, Opcodes.IF_ICMPGE, 498, 308, 155, TypedValues.CycleType.TYPE_CUSTOM_WAVE_SHAPE, 907, 817, Opcodes.NEW, 62, 16, TypedValues.CycleType.TYPE_WAVE_PHASE, 535, 336, 286, 437, 375, 273, TypedValues.MotionType.TYPE_QUANTIZE_MOTIONSTEPS, 296, Opcodes.INVOKESPECIAL, 923, 116, 667, 751, 353, 62, 366, 691, 379, 687, 842, 37, 357, ResolutionCameraConfig.IMAGE_QUALITY_720P, 742, 330, 5, 39, 923, 311, TypedValues.CycleType.TYPE_WAVE_OFFSET, 242, 749, 321, 54, 669, TypedValues.AttributesType.TYPE_PATH_ROTATE, 342, 299, 534, 105, 667, 488, CodeUtils.DEFAULT_REQ_HEIGHT, 672, 576, 540, TypedValues.AttributesType.TYPE_PATH_ROTATE, 486, 721, TypedValues.MotionType.TYPE_QUANTIZE_MOTIONSTEPS, 46, 656, 447, 171, 616, 464, 190, 531, 297, 321, 762, 752, 533, 175, GattError.GATT_CMD_STARTED, 14, 381, 433, 717, 45, 111, 20, 596, 284, 736, GattError.GATT_MORE, 646, 411, 877, 669, GattError.GATT_ENCRYPTED_NO_MITM, 919, 45, 780, 407, 164, 332, 899, Opcodes.IF_ACMPEQ, 726, 600, 325, 498, 655, 357, 752, 768, 223, 849, 647, 63, 310, 863, 251, 366, 304, 282, 738, 675, 410, 389, 244, 31, 121, 303, 263}};

    private PDF417ErrorCorrection() {
    }

    static int getErrorCorrectionCodewordCount(int i) {
        if (i < 0 || i > 8) {
            throw new IllegalArgumentException("Error correction level must be between 0 and 8!");
        }
        return 1 << (i + 1);
    }

    static int getRecommendedMinimumErrorCorrectionLevel(int i) throws WriterException {
        if (i <= 0) {
            throw new IllegalArgumentException("n must be > 0");
        }
        if (i <= 40) {
            return 2;
        }
        if (i <= 160) {
            return 3;
        }
        if (i <= 320) {
            return 4;
        }
        if (i <= 863) {
            return 5;
        }
        throw new WriterException("No recommendation possible");
    }

    static String generateErrorCorrection(CharSequence charSequence, int i) {
        int errorCorrectionCodewordCount = getErrorCorrectionCodewordCount(i);
        char[] cArr = new char[errorCorrectionCodewordCount];
        int length = charSequence.length();
        for (int i2 = 0; i2 < length; i2++) {
            int i3 = errorCorrectionCodewordCount - 1;
            int iCharAt = (charSequence.charAt(i2) + cArr[i3]) % PDF417Common.NUMBER_OF_CODEWORDS;
            while (i3 >= 1) {
                cArr[i3] = (char) ((cArr[i3 - 1] + (929 - ((EC_COEFFICIENTS[i][i3] * iCharAt) % PDF417Common.NUMBER_OF_CODEWORDS))) % PDF417Common.NUMBER_OF_CODEWORDS);
                i3--;
            }
            cArr[0] = (char) ((929 - ((iCharAt * EC_COEFFICIENTS[i][0]) % PDF417Common.NUMBER_OF_CODEWORDS)) % PDF417Common.NUMBER_OF_CODEWORDS);
        }
        StringBuilder sb = new StringBuilder(errorCorrectionCodewordCount);
        for (int i4 = errorCorrectionCodewordCount - 1; i4 >= 0; i4--) {
            char c = cArr[i4];
            if (c != 0) {
                cArr[i4] = (char) (929 - c);
            }
            sb.append(cArr[i4]);
        }
        return sb.toString();
    }
}
