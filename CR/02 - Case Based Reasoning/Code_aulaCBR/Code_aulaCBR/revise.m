function [journey, new_case] = revise(retrieved_cases, new_case, new_price)
    
    retrieved_codes = retrieved_cases{:,1};
    code = str2double('-');
        
    while isnan(code) || fix(code) ~= code || ismember(code, retrieved_codes) == 0
        fprintf('From the retrieved cases, which is the one that better matches your journey?\n');
        code = str2double(input('Journey Code: ','s'));
    end
    
    journey = fix(code);
    
    fprintf('\nUpdate your journey price with the new estimated value? (y/n)\n');
    option = input('Option: ', 's');

    if option == 'y' || option == 'Y'
        new_case.price = new_price;
    end

    fprintf('\nUpdate the Holiday Type? (y/n)\n');
    option = input('Option: ', 's');

    if option == 'y' || option == 'Y'
        holiday_type = input('Active, Bathing, City, Education, Language, Recreation, Skiing, Wandering: ', 's');
        new_case.holiday_type = holiday_type;
    end

    fprintf('\nUpdate the number of people ? (y/n)\n');
    option = input('Option: ', 's');

    if option == 'y' || option == 'Y'
        number_persons = str2double (input('Number of People: ', 's'));
        new_case.number_persons = number_persons;
    end

    fprintf('\nUpdate the region? (y/n)\n');
    option = input('Option: ', 's');

    if option == 'y' || option == 'Y'
        region = input('Region: ', 's');
        new_case.region = region;
    end

    fprintf('\nUpdate the transportation? (y/n)\n');
    option = input('Option: ', 's');

    if option == 'y' || option == 'Y'
        transportation = input('Car, Coach, Plane or Train: ', 's');
        new_case.transportation = transportation;
    end

    fprintf('\nUpdate the duration? (y/n)\n');
    option = input('Option: ', 's');

    if option == 'y' || option == 'Y'
        duration = str2double(input('Duration: ', 's'));
        new_case.duration = duration;
    end

    fprintf('\nUpdate the season? (y/n)\n');
    option = input('Option: ', 's');

    if option == 'y' || option == 'Y'
        season = input('Season: ', 's');
        new_case.season = season;
    end

    fprintf('\nUpdate the accommodation? (y/n)\n');
    option = input('Option: ', 's');

    if option == 'y' || option == 'Y'
        accommodation = input('HolidayFlat, OneStar, TwoStars, ThreeStars, FourStars or FiveStars: ', 's');
        new_case.accommodation = accommodation;
    end
end