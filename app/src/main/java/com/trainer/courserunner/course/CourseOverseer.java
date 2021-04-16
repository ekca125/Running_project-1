package com.trainer.courserunner.course;

import android.os.AsyncTask;
import android.util.Log;

import com.trainer.courserunner.drawtype.DrawingAddress;
import com.trainer.courserunner.drawtype.DrawingPath;
import com.trainer.courserunner.maps.MapDrawer;
import com.trainer.courserunner.rooms.AppDatabase;
import com.trainer.courserunner.rooms.AppDatabaseLoader;
import com.trainer.courserunner.rooms.CoursePath;
import com.trainer.courserunner.rooms.UserCourseInfo;
import com.trainer.courserunner.rooms.UserCourseInfoDao;

import java.util.List;
import java.util.Map;

public class CourseOverseer{
    MapDrawer mapDrawer;
    public CourseOverseer(MapDrawer mapDrawer){
        this.mapDrawer=mapDrawer;
    }
    //그리기
    CoursePath[] courseFlags;
    Object[] courseOverlayMarkers;
    Object courseOverlayPath;
    long courseId;
    private void drawMap(long courseId){
        //코스 설정
        this.courseId=courseId;
        //코스 데이터 호출
        courseFlags = AppDatabaseLoader.getAppDatabase().coursePathDao().loadCoursePath(courseId);
        //그리기
        DrawingPath drawingPath=new DrawingPath(courseFlags);
        courseOverlayPath=mapDrawer.drawCourse(drawingPath);
        courseOverlayMarkers=mapDrawer.drawOverlayMarkers(drawingPath);
        //
    }

    long usercourse_id;
    //신규시작
    public void startOversight(long courseId){
        drawMap(courseId);
        //사용자 코스 등록
        UserCourseInfo userCourseInfo = new UserCourseInfo();
        userCourseInfo.course_id=courseId;
        usercourse_id=AppDatabaseLoader.getAppDatabase().userCourseInfoDao().insertUserCourseInfo(userCourseInfo);
    }
    /*
    //일시정지 시작
    public void startOversight(long courseId,long usercourse_id){
        startOversight(courseId);
        //불러와서 마커들 제거 + 현재까지 걸었던 경로 그리기

    }
    */

    public void updateLocation(double latitude, double longtitude){



    }

    


}
