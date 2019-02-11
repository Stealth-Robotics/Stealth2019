/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.util;
@Deprecated
/**
 * A class to pass vars by refrence
 * Uses Generics to make it so that we can use mutiple types
 */
public class Reference<Type> {

    public Type value;

    public Reference(){
        
    }

    public Reference(Type value){
        this.value = value;
    }
}
