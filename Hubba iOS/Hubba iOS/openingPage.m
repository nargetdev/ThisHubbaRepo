//
//  openingPage.m
//  Hubba iOS
//
//  Created by Jackson on 11/7/13.
//  Copyright (c) 2013 eecs499. All rights reserved.
//

#import "openingPage.h"

@interface openingPage ()

@end

@implementation openingPage

@synthesize login, loginBut, signup, signupBut;

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
  
  CAGradientLayer *gradient = [CAGradientLayer layer];
  [gradient setFrame:login.frame];
  [gradient setColors:[NSArray arrayWithObjects:(id)GRAY2.CGColor, (id)[UIColor blackColor].CGColor, (id)GRAY2.CGColor, nil]];
  CAGradientLayer *gradient2 = [CAGradientLayer layer];
  [gradient2 setFrame:signup.frame];
  [gradient2 setColors:[NSArray arrayWithObjects:(id)GRAY2.CGColor, (id)[UIColor blackColor].CGColor, (id)GRAY2.CGColor, nil]];
  [loginBut.layer insertSublayer:gradient atIndex:0];
  [signupBut.layer insertSublayer:gradient2 atIndex:0];
  [loginBut.layer setBorderColor: [UIColor blackColor].CGColor];
  [signupBut.layer setBorderColor: [UIColor blackColor].CGColor];
  [loginBut.layer setBorderWidth:1];
  [signupBut.layer setBorderWidth:1];
  
  
  [self.view setBackgroundColor:GRAY1];

  if( self.view.frame.size.width == 320 ){
    NSLog(@"Vertical");
    [signupBut setCenter:CGPointMake(self.view.center.x, DEVICEHEIGHT-75)];
    [loginBut setCenter:CGPointMake(self.view.center.x, DEVICEHEIGHT-145)];
  }
  else{
    NSLog(@"Horizontal");
    [signupBut setCenter:CGPointMake(self.view.center.x, self.view.center.y+35)];
    [loginBut setCenter:CGPointMake(self.view.center.x, self.view.center.y-35)];
  }
 
  [[NSNotificationCenter defaultCenter] addObserver:self
                                           selector:@selector(orientationChanged)
                                               name:UIDeviceOrientationDidChangeNotification
                                             object:nil];
  
  [[UIApplication sharedApplication] setStatusBarStyle:UIStatusBarStyleLightContent];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

-(void)orientationChanged{
  UIInterfaceOrientation orientation = [[UIApplication sharedApplication] statusBarOrientation];
  if( UIInterfaceOrientationIsPortrait(orientation)){
    NSLog(@"Vertical");
    [signupBut setCenter:CGPointMake(self.view.center.x, DEVICEHEIGHT-75)];
    [loginBut setCenter:CGPointMake(self.view.center.x, DEVICEHEIGHT-145)];
  }
  else{
    NSLog(@"Horizontal");
    [signupBut setCenter:CGPointMake(self.view.center.x, self.view.center.y+35)];
    [loginBut setCenter:CGPointMake(self.view.center.x, self.view.center.y-35)];
  }

}

@end