tasks {
    task("1.1.1") {
        name = "Пирамидальная сортировка"
        maxScore = 1
        softDeadline = "13/09/2025"
        hardDeadline = "13/09/2025"
    }
    task("1.2.2") {
        name = "Хеш-таблица"
        maxScore = 1
        softDeadline = "08/11/2025"
        hardDeadline = "15/11/2025"
    }
}

groups {
    group("24214") {
        student("VlanAni") {
            name = "Анисимов Владимир Сергеевич"
            repositoryUrl = "https://github.com/VlanAni/OOP.git"
        }
        student("agrentseva") {
            name = "Гренцева Алина Олеговна"
            repositoryUrl = "https://github.com/agrentseva/OOP.git"
        }
        student("DeshinMichael") {
            name = "Дешин Михаил Александрович"
            repositoryUrl = "https://github.com/DeshinMichael/OOP.git"
        }
        student("Proletcultist") {
            name = "Зенин Матвей Вадимович"
            repositoryUrl = "https://github.com/Proletcultist/OOP.git"
        }
        student("pkrasnyanskii") {
            name = "Краснянский Пётр Михайлович"
            repositoryUrl = "https://github.com/pkrasnyanskii/OOP.git"
        }
        student("NetscapeNav") {
            name = "Кученков Степан Андреевич"
            repositoryUrl = "https://github.com/NetscapeNav/OOP.git"
        }
        student("dmObraztsov") {
            name = "Образцов Дмитрий Евгеньевич"
            repositoryUrl = "https://github.com/dmObraztsov/OOP.git"
        }
        student("Yojik1-cpu") {
            name = "Пешков Алексей Максимович"
            repositoryUrl = "https://github.com/Yojik1-cpu/OOP.git"
        }
        student("NikRo12") {
            name = "Романенко Никита Сергеевич"
            repositoryUrl = "https://github.com/NikRo12/OOP.git"
        }
        student("Marat-nsu") {
            name = "Тимофеев Марат Вадимович"
            repositoryUrl = "https://github.com/Marat-nsu/OOP.git"
        }
        student("chebupelka332-pro") {
            name = "Токарев Максим Константинович"
            repositoryUrl = "https://github.com/chebupelka332-pro/OOP.git"
        }
    }
}

submissions {
    submission("VlanAni", "1.1.1") { bonus = 0 }
    submission("VlanAni", "1.2.2") { bonus = 0 }
    submission("agrentseva", "1.1.1") { bonus = 0 }
    submission("agrentseva", "1.2.2") { bonus = 0 }
    submission("DeshinMichael", "1.1.1") { bonus = 0 }
    submission("DeshinMichael", "1.2.2") { bonus = 0 }
    submission("Proletcultist", "1.1.1") { bonus = 0 }
    submission("Proletcultist", "1.2.2") { bonus = 0 }
    submission("pkrasnyanskii", "1.1.1") { bonus = 0 }
    submission("pkrasnyanskii", "1.2.2") { bonus = 0 }
    submission("NetscapeNav", "1.1.1") { bonus = 0 }
    submission("NetscapeNav", "1.2.2") { bonus = 0 }
    submission("dmObraztsov", "1.1.1") { bonus = 0 }
    submission("dmObraztsov", "1.2.2") { bonus = 0 }
    submission("Yojik1-cpu", "1.1.1") { bonus = 0 }
    submission("Yojik1-cpu", "1.2.2") { bonus = 0 }
    submission("NikRo12", "1.1.1") { bonus = 0 }
    submission("NikRo12", "1.2.2") { bonus = 0 }
    submission("Marat-nsu", "1.1.1") { bonus = 0 }
    submission("Marat-nsu", "1.2.2") { bonus = 0 }
    submission("chebupelka332-pro", "1.1.1") { bonus = 0 }
    submission("chebupelka332-pro", "1.2.2") { bonus = 0 }
}

checkpoints {
    checkpoint("Checkpoint 1") { date = "01/03/2026" }
    checkpoint("Checkpoint 2") { date = "01/05/2026" }
}

settings {
    softDeadlinePenalty = 0.5
    maxBonus = 1.0
    testTimeoutSeconds = 60
}