//
//  ViewController.swift
//  BiometricBigHero
//
//  Created by TuyenBQ on 6/1/18.
//  Copyright © 2018 TuyenBQ. All rights reserved.
//

import UIKit

class ViewController: UIViewController {

    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }

    @IBAction func letMeInButtonClicked(_ sender: Any) {
        if BiometricVendor.sharedInstance.beAbleToAuthenticateByBiometry() {
            BiometricVendor.sharedInstance.authenticate(reason: "Authentication is required for access", completion: {
                print("Welcome to the moon")
            }) { (error) in
                print("Failed")
            }
        }
    }
    
}

