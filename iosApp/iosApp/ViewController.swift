//
//  ViewController.swift
//  iosApp
//
//  Created by Ashley Figueira on 22/01/2019.
//  Copyright © 2019 Ashley Figueira. All rights reserved.
//

import UIKit
import shared

class ViewController: UIViewController {

    override func viewDidLoad() {
        super.viewDidLoad()

        let label = UILabel(frame: CGRect(x: 0, y: 0, width: 300, height: 21))
        label.center = CGPoint(x: 160, y: 285)
        label.textAlignment = .center
        label.font = label.font.withSize(25)
        //label.text = CommonKt.createApplicationScreenMessage()
        
        WeatherApi().fetchWeather(successCallback: { weather in
                label.text = weather.city
                return KotlinUnit()
            }
            ,errorCallback: { error in
                label.text = error.message
                print(error.message)
                return KotlinUnit()
            }
        )
        view.addSubview(label)
    }


}

