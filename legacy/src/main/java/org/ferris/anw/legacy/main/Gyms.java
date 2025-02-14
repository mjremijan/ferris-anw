/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.ferris.anw.legacy.main;

import java.util.List;

/**
 *
 * @author Michael
 */
public class Gyms {
    
    private static List<String> gyms = List.of( 
          "Athletes in Motion"	//Prairieville, LA
        , "Airbenders"	//Owasso, OK
        , "Altius Ninja"	//Franklin, WI
        , "Authentik Movement Training"	//Cleveland, OH
        , "Olympia Ninja City"	//Manchester, MO
        , "Big Time Ninja"	//Bolingbrook, IL
        , "Capital Warriors"	//Jefferson City, MO
        , "Conquer Gym - Gilbert"	//Queen Creek, AZ
        , "District Christian Academy"	//El Dorado Hills, CA
        , "The Barn"	//Dorsey, IL
        , "Hit Squad Ninjas"	//Tempe, AZ
        , "Fortified Fitness"	//Murfreesboro, TN
        , "The Vault Ninja"	//St Joseph, MO
        , "Iron Sports Ninja Warrior"	//Houston, TX
        , "Magic City Gymnastics"	//Billings, MT
        , "Motus - Lee Summit"	//Lee's Summit, MO
        , "Motus - Overland Park"	//Overland Park, KS
        , "Next Level Gym"	//Stillwater, OK
        , "Ninja Intensity"	//Castle Rock, CO
        , "NinjaKour"	//Lilburn, GA
        , "Ninja Lair"	//Las Vegas, NV
        , "Ninja Obstacle Academy"	//Huntsville, AL
        , "Flip Side"	//Lowell, AR
        , "Ninjobstacles"	//Kettering, OH
        , "ORTHDX"	//Madison, WI
        , "Paragon Performance Sports"	//Manhattan, KS
        , "The Ninja Playground"	//Lehi, UT
        , "Republic Warrior Sports"	//Republic, MO
        , "Nebraska Ninja"	//Suite H. Lincoln, NE
        , "Permian Basin Ninja Academy"	//Midland, TX
        , "Power Pickle"	//Aurora, TX
        , "Warrior Zone Ninja Obstacle Center"	//Winterville, NC
        , "Shinobi Fitness"	//Cottage Hills, IL
        , "Springfield Warrior Sport"	//Springfield, MO
        , "Sumner Ninja"	//Sumner, WA
        , "Super Ninja Zone"	//Cuyahoga Falls, OH
        , "Surge Ninja"	//Hopkinton, MA
        , "The Ninja Gym"	//Collierville, TN
        , "WISC Ninja Warrior"	//Williamsburg, VA
        , "Tri County"	//Fenton, MI
        , "UNAA Gym"	//Los Ranchos De Albuquerque, NM
        , "Ultimate Ninjas - Elmhurst"	//Elmhurst, IL
        , "Ultimate Ninjas - Chesterfield"	//Chesterfield, MO
        , "Upward Ninja"	//Siloam Springs, AR
        , "Warrior Tech - Raleigh"	//Morrisville, NC
        , "World Class Dragons"	//Belleville, IL
        , "605 Ninja"	//Sioux Falls, SD
        , "716 Ninja Academy"	//Lancaster, NY
        , "Altitude Movement Gym"	//Colorado Springs, CO
        , "Avant Coeur Ninja"	//Coeur d'Alene, ID
        , "Bayside Sports Academy"	//Saint Petersburg, FL
        , "Camp Ninja Warrior"	//Bixby, OK
        , "Camp Rhino"	//Las Vegas, NV
        , "Conquer Ninja - Fargo"	//Fargo, ND
        , "Conquer Ninja Gyms - Woodbury"	//Woodbury, MN
        , "Flow Vault"	//Thornton, CO
        , "Four Star Ninja Academy"	//Fargo, ND
        , "Free Spirit"	//Bend, OR
        , "Go Ninja"	//Lancaster, CA
        , "Impact Ninja Gym"	//Lehi, UT
        , "Kings Camp & Fitness"	//San Carlos, CA
        , "Leap Ninja"	//Laramie, WY
        , "Lost Island"	//Colorado Springs, CO
        , "Move Sport Ninja Academy"	//Pflugerville, TX
        , "Ninja Force"	//Albuquerque, NM
        , "Ninjas United"	//Maple Grove, MN
        , "Nova Ninja Sterling"	//Sterling, VA
        , "Rock Solid Warrior - Apex"	//Apex, NC
        , "Rock Solid Warrior - Fuquay"	//Fuquay-Varina, NC
        , "Sterling Gym"	//Sterling, MA
        , "Sumter Ninja Warrior"	//Sumter, SC
        , "The Grip"	//St George, UT
        , "The Jungle Movement Academy"	//Round Rock, TX
        , "The Wolfs Den"	//SAN BERNARDINO, CA
        , "Tumble Time Gymnastics"	//Cameron Park, CA
        , "Ultimate Ninjas- Anaheim Hills"	//Anaheim, CA
        , "USA Ninja Challenge - Marlborough"	//Marlborough, MA
        , "USA Ninja Challenge-Manchester"	//Manchester, NH
        , "Vertex Lab Academy"	//Alexandria, VA
        , "Warrior Challenge Arena"	//Broomfield, CO
        , "Kid Wonder"	//Encinitas, CA
        , "Fighting Lyons"	//Essendon North, Victoria
        , "Rugged Obstacle Courses" //Williamsburg, VA
        , "Queen City Ninja"    //Cincinnati, OH
        , "WA Ninja Games"  // Australia
        , "The Warrior Factory - Hamilton"  //Hamilton, Ontario, Canada
        , "Ultimate Ninja Gym"  //Albuquerque, NM
        , "Ninja Hub"   //Ohio
        , "Ninja Fortress"  //Washington
        , "Bodies In Motion"    //Idaho
        , "USA Ninja Challenge - Bristol"   //Bristol, VA
        , "305 Ninja Academy"   //Miami, Florida
        , "Conquer Gym - Blaine"    //Blaine, Minnesota
        , "Ninja Nation - Murphy"   //Texas
        , "Get Over It Fitness"     //Tennessee
        , "Ninja Quest Adventures"  //Issaquah, WA
        , "Warrior Playground"   //Longmont, CO 
        , "Wojo Ninja Warrior"
        , "TA Fitness Weymouth"
        , "Adrenaline Monkey West Dundee"
        , "Australian Warrior Fitness"
        , "Hybrid Ninja Academy"
        , "Obstacle Course Racing School"   
        , "Conquer Gym - Peoria"
        , "USA Ninja Challenge - Greenbrier" //VA
        , "USA Ninja Challenge - Rochester"  //NY
        , "Strong Grip" //Forida
        , "Conquer Gym - Chandler" //AZ
        , "USA Ninja Challenge - Katy" //TX
        , "Rapt Ninja Academy" //	Massachusetts
        , "Ninja Republic" //	California
        , "First State Gymnatics" //	Delaware
        , "The Rock Ninja Warrior Academy"  // Orange Park, FL
        , "Conquer Ninja Gym - Bedford" // - Bedford	Texas
        , "Beast Ninja" //South Carolina
        , "StricklyNinjas" //Georgia
        , "SA Base Camp" // Australia
    );

    public static boolean contains(String gymName) {
        return gyms.contains(gymName);
    }
    
    static String find(String name) {
        if (name.equals("Rapt Ninja")) {
            name = "Rapt Ninja Academy";
        }
        else
        if (name.equals("Buffalo Elite Ninja")) {
            name = "716 Ninja Academy";
        }
        else
        if (name.equals("The Wolf's Den")) {
            name = "The Wolfs Den";
        }
        else
        if (name.equals("Hitsquad Ninja Gym") || name.equals("Hit Squad")) {
            name = "Hit Squad Ninjas";
        }
        else
        if (name.equals("NCNS (Qualified Athletes Only)")) {
            name = "605 Ninja";
        }
        else
        if (name.equals("Move Sport Ninja")) {
            name = "Move Sport Ninja Academy";
        }
        else
        if (name.equals("Ninja Obstacle Academy Huntsville")) {
            name = "Ninja Obstacle Academy";
        }
        else
        if (name.equals("Ultimate Ninjas Anaheim Hills")) {
            name = "Ultimate Ninjas- Anaheim Hills";
        }
        else
        if (name.equals("Ultimate Ninjas - Anaheim")) {
            name = "Ultimate Ninjas- Anaheim Hills";
        }
        else
        if (name.equals("Ultimate Ninjas- Anaheim")) {
            name = "Ultimate Ninjas- Anaheim Hills";
        }
        else
        if (name.equals("Ninja Core Training (Lost Island Warrior)")) {
            name = "Lost Island";
        }
        else
        if (name.equals("Ultimate Ninja Gym")) {
            name = "UNAA Gym";
        }
        else
        if (name.equals("AirBenders")) {
            name = "Airbenders";
        }
        else
        if (name.equals("Nova Ninja")) {
            name = "Nova Ninja Sterling";
        }
        else
        if (name.equals("Conquer Gym - Fargo")) {
            name = "Conquer Ninja - Fargo";
        }
        else
        if (name.equals("Next Level")) {
            name = "Next Level Gym";
        }
        else
        if (name.equals("Iron Sports")) {
            name = "Iron Sports Ninja Warrior";
        }
        else
        if (name.equals("Warrior Zone")) {
            name = "Warrior Zone Ninja Obstacle Center";
        }
        else
        if (name.equals("Conquer Ninja Gym Blaine")) {
            name = "Conquer Gym - Blaine";
        }
        else
        if (name.equals("KidWonder")) {
            name = "Kid Wonder";
        }
        else
        if (name.equals("Conquer Ninja Gym Peoria")) {
            name = "Conquer Gym - Peoria";
        }
        else
        if (name.equals("Authentik Movement")) {
            name = "Authentik Movement Training";
        }
        else
        if (name.equals("Vertex Labs Academy")) {
            name = "Vertex Lab Academy";
        }
        else
        if (name.equals("Ninja Nation Murphy")) {
            name = "Ninja Nation - Murphy";
        }
        else
        if (name.equals("Tri County Ninja")) {
            name = "Tri County";
        }
        
        //  
        return name;
    }
}
