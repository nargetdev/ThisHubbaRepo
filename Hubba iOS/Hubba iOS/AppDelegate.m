//
//  AppDelegate.m
//  Hubba iOS
//
//  Created by Jackson on 10/29/13.
//  Copyright (c) 2013 eecs499. All rights reserved.
//

#import "AppDelegate.h"
#import "openingPage.h"

@implementation AppDelegate

- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions
{
  // Override point for customization after application launch.
  self.window = [[UIWindow alloc] initWithFrame:[[UIScreen mainScreen] bounds]];
  
  // light status bar.
  [[UIApplication sharedApplication] setStatusBarStyle:UIStatusBarStyleDefault];
  
  // sets up title screens to open.
  openingPage *titleScreen = [[openingPage alloc] initWithNibName:@"openingPage" bundle:nil];
  UINavigationController *masterNav = [[UINavigationController alloc] initWithRootViewController:titleScreen];
  [self.window setRootViewController:masterNav];
  [masterNav setNavigationBarHidden:YES];
  [self.window makeKeyAndVisible];
  
  UIImageView  *bg = [ [ UIImageView alloc ] initWithImage: [ UIImage imageNamed: @"bg.jpg" ] ];
  bg.frame = self.window.bounds;
  bg.contentMode = UIViewContentModeScaleAspectFill;
  [ self.window addSubview: bg ];
  [ self.window sendSubviewToBack: bg ];
  return YES;
}
							
- (void)applicationWillResignActive:(UIApplication *)application
{
  // Sent when the application is about to move from active to inactive state. This can occur for certain types of temporary interruptions (such as an incoming phone call or SMS message) or when the user quits the application and it begins the transition to the background state.
  // Use this method to pause ongoing tasks, disable timers, and throttle down OpenGL ES frame rates. Games should use this method to pause the game.
}

- (void)applicationDidEnterBackground:(UIApplication *)application
{
  // Use this method to release shared resources, save user data, invalidate timers, and store enough application state information to restore your application to its current state in case it is terminated later. 
  // If your application supports background execution, this method is called instead of applicationWillTerminate: when the user quits.
}

- (void)applicationWillEnterForeground:(UIApplication *)application
{
  // Called as part of the transition from the background to the inactive state; here you can undo many of the changes made on entering the background.
}

- (void)applicationDidBecomeActive:(UIApplication *)application
{
  // Restart any tasks that were paused (or not yet started) while the application was inactive. If the application was previously in the background, optionally refresh the user interface.
}

- (void)applicationWillTerminate:(UIApplication *)application
{
  // Called when the application is about to terminate. Save data if appropriate. See also applicationDidEnterBackground:.
}

@end
