
package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
// import edu.wpi.cscore.UsbCamera;
// import edu.wpi.first.cameraserver.*;
// import frc.robot.util.*;

@Deprecated
/**
 * Sets up the camera
 */
public class Vision extends Subsystem 
{
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
  
    public Vision() 
    {
        // new Thread(() -> 
        // {
        //     UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
        //     camera.setResolution(640, 480);
        //     camera.setFPS(25);
        // }).start();
    
        // SmartDashboard.putString("Vision/Status", Status.Good.toString());
    
        // SmartDashboard.putNumber("Vision/TestNum", 10.25);
    }
  
  
    @Override
    public void initDefaultCommand() 
    {
        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }
}
