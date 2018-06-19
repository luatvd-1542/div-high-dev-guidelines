//
//  BiometricVendor.swift
//  BiometricBigHero
//
//  Created by TuyenBQ on 6/1/18.
//  Copyright © 2018 TuyenBQ. All rights reserved.
//

import UIKit
import LocalAuthentication

/// success block
public typealias AuthenticationSuccess = (() -> ())

/// failure block
public typealias AuthenticationFailure = ((_ error: NSError) -> ())

protocol BiometricVendorAdaptation {
    func beAbleToAuthenticateByBiometry() -> Bool // TouchID or FaceID still be here.
    func touchIDAvailable() -> Bool // Device supports Touch ID
    func faceIDAvailable() -> Bool // Device support Face ID
    func authenticate(reason: String, completion successBlock : AuthenticationSuccess?, failed failureBlock: AuthenticationFailure?) -> Void // Authentication
}

class BiometricVendor: NSObject {
    //MARK: Singleton if needed
    private override init() {}
    static let sharedInstance = BiometricVendor()
    
    //MARK: Inner Variables
    fileprivate let context = LAContext()
}

extension BiometricVendor: BiometricVendorAdaptation {
    
    func beAbleToAuthenticateByBiometry() -> Bool {
        var error: NSError?
        let biometricAbility = LAContext().canEvaluatePolicy(.deviceOwnerAuthenticationWithBiometrics, error: &error)
        // Biometry is available on the device
        return biometricAbility
    }
    
    func touchIDAvailable() -> Bool {
        if self.beAbleToAuthenticateByBiometry() == true {
            return (self.context.biometryType == LABiometryType.touchID)
        }
        return false
    }
    
    func faceIDAvailable() -> Bool {
        if self.beAbleToAuthenticateByBiometry() == true {
            return (self.context.biometryType == LABiometryType.faceID)
        }
        return false
    }
    
    func authenticate(reason: String, completion successBlock: AuthenticationSuccess?, failed failureBlock: AuthenticationFailure?) {
        context.evaluatePolicy(.deviceOwnerAuthenticationWithBiometrics, localizedReason: reason) { (success, error) in
            if success == true {
                successBlock?()
            } else {
                let error = NSError(domain: "", code: -1, userInfo: nil)
                failureBlock?(error)
            }
        }
    }
}
