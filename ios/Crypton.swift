//
//  Crypton.swift
//  RNCrypton
//
//  Created by AmirHossein on 6/2/19.
//  Copyright © 2019 Facebook. All rights reserved.
//

import Foundation

@objc(Crypton)
class Crypton: NSObject {
    
    @objc(Hi:message:)
    func Hi(name: String, message: String) -> Void {
        // Date is ready to use!
        return message
    }
    
    @objc
    func constantsToExport() -> [String: Any]! {
        return ["someKey": "someValue"]
    }
    
}
