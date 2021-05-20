package com.trainer.courserunner.course.component.sounder;

import com.trainer.courserunner.Application.AppFunctionLoader;
import com.trainer.courserunner.Application.sound.GuideSound;
import com.trainer.courserunner.Application.sound.VoiceType;
import com.trainer.courserunner.course.component.CourseComponent;
import com.trainer.courserunner.rooms.AppDatabase;
import com.trainer.courserunner.rooms.UserCourseFlagDerived;

public class CourseSounderGuide extends CourseComponent {
    Long courseId;
    Long userCoursedId;
    boolean percentFlag25;
    boolean percentFlag50;
    boolean percentFlag75;
    boolean percentFlag100;
    VoiceType voiceType;

    public CourseSounderGuide(Long courseId, Long userCoursedId) {
        this.courseId = courseId;
        this.userCoursedId = userCoursedId;

        percentFlag25 = false;
        percentFlag50 = false;
        percentFlag75 = false;
        percentFlag100 = false;
    }

    public void setVoiceType(VoiceType voiceType) {
        this.voiceType = voiceType;
    }

    @Override
    protected Object runInWorkThread() {
        /*
            0~100 : 25, 50, 75, 100
            0~50 : 12.5, 25, 37.5 ,50
            0~1 : 0.25, 0.5, 0.75 1
            0~n : (int)0.25*n 0.5*n 0.75*n 1*n

            ex)
            0~12 : 3, 6, 9 ,12
        */



        AppDatabase appDatabase = AppFunctionLoader.getAppDatabase();
        int flagCount = appDatabase.courseFlagDao().getCountCourseMarkerFlags(courseId);
        int passedFlagCount = UserCourseFlagDerived.getCountUnvistedUserCourseFlags(courseId, userCoursedId);

        if (passedFlagCount == (int) ((double) flagCount * 0.25)) {
            //25%지점
            if (!percentFlag25) {
                //처음으로 지나가는 경우
                percentFlag25 = true;
                if (voiceType == VoiceType.MALE) {
                    return new SoundCommandGuide(GuideSound.FINISHMAN);
                } else if (voiceType == VoiceType.FEMALE) {
                    return new SoundCommandGuide(GuideSound.FINISHWOMAN);
                } else if (voiceType == VoiceType.CHILD) {
                    return new SoundCommandGuide(GuideSound.FINISHKID);
                }
            }

        } else if (passedFlagCount == (int) ((double) flagCount * 0.50)) {
            //50%지점
            if (!percentFlag50) {
                //처음으로 지나가는 경우
                percentFlag50 = true;
                if (voiceType == VoiceType.MALE) {
                    return new SoundCommandGuide(GuideSound.FINISHMAN);
                } else if (voiceType == VoiceType.FEMALE) {
                    return new SoundCommandGuide(GuideSound.FINISHWOMAN);
                } else if (voiceType == VoiceType.CHILD) {
                    return new SoundCommandGuide(GuideSound.FINISHKID);
                }
            }
        } else if (passedFlagCount == (int) ((double) flagCount * 0.75)) {
            //75%지점
            if (!percentFlag75) {
                //처음으로 지나가는 경우
                percentFlag75 = true;
                if (voiceType == VoiceType.MALE) {
                    return new SoundCommandGuide(GuideSound.FINISHMAN);
                } else if (voiceType == VoiceType.FEMALE) {
                    return new SoundCommandGuide(GuideSound.FINISHWOMAN);
                } else if (voiceType == VoiceType.CHILD) {
                    return new SoundCommandGuide(GuideSound.FINISHKID);
                }
            }
        } else if (passedFlagCount == (int) ((double) flagCount * 1.0)) {
            //100%지점
            if (!percentFlag100) {
                //처음으로 지나가는 경우
                percentFlag100 = true;
                if (voiceType == VoiceType.MALE) {
                    return new SoundCommandGuide(GuideSound.FINISHMAN);
                } else if (voiceType == VoiceType.FEMALE) {
                    return new SoundCommandGuide(GuideSound.FINISHWOMAN);
                } else if (voiceType == VoiceType.CHILD) {
                    return new SoundCommandGuide(GuideSound.FINISHKID);
                }
            }
        }
        return null;
    }

    protected void runInUiThread(Object object) {
        super.runInUiThread(object);
        if (object != null) {
            ((SoundCommand) object).execute();
        }
    }
}
