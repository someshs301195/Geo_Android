DROP TABLE place;

CREATE TABLE place (
  name TEXT PRIMARY KEY,
  description TEXT,
  category TEXT,
  address_title TEXT,
  address_street TEXT,
  elevation REAL,
  latitude REAL,
  longitude REAL);

INSERT INTO place VALUES
   ('ASU-West', 
    'Home of ASU Applied Computing Program', 
    'School',
    'ASU West Campus',
    '13591 N 47th Ave$Phoenix AZ 85051',
    1100.0, 33.608979, -112.159469);

INSERT INTO place VALUES
   ('UAK-Anchorage',
    'University of Alaska largest campus',
    'School',
    'University of Alaska at Anchorage',
    '290 Spirit Dr$Anchorage AK 99508',
    0.0, 61.189748, -149.826721);

INSERT INTO place VALUES
   ('Toreros',
    'The University of San Diego, a private Catholic undergraduate university.',
    'School',
    'University of San Diego',
    '5998 Alcala Park$San Diego CA 92110',
    200.0, 32.771923, -117.188204);

INSERT INTO place VALUES
   ('Barrow-Alaska',
    'The only real way in and out of Barrow Alaska.',
    'Travel',
    'Will Rogers Airport',
    '1725 Ahkovak St$Barrow AK 99723',
    5.0, 71.287881, -156.779417);

INSERT INTO place VALUES
   ('Calgary-Alberta',
    'The home of the Calgary Stampede Celebration',
    'Travel',
    'Calgary International Airport',
    '2000 Airport Rd NE$Calgary AB T2E 6Z8 Canada',
    3556,51.131377,-114.011246);

INSERT INTO place VALUES
   ('London-England',
    'Renaissance Hotel at the Heathrow Airport',
    'Travel',
    'Renaissance London Heathrow Airport',
    '5 Mondial Way$Harlington Hayes UB3 UK',
    82,51.481959,-0.445286);

INSERT INTO place VALUES
   ('Moscow-Russia',
    'The Marriott Courtyard in downtown Moscow',
    'Travel',
    'Courtyard Moscow City Center',
    'Voznesenskiy per 7 $ Moskva Russia 125009',
    512,55.758666,37.604058);

INSERT INTO place VALUES
   ('New-York-NY',
    'New York City Hall at West end of Brooklyn Bridge',
    'Travel',
    'New York City Hall',
    '1 Elk St$New York NY 10007',
    2,40.712991,-74.005948);

INSERT INTO place VALUES
   ('Notre-Dame-Paris',
    'The 13th century cathedral with gargoyles, one of the first flying buttress, and home of the purported crown of thorns.',
    'Travel',
    'Cathedral Notre Dame de Paris',
    '6 Parvis Notre-Dame Pl Jean-Paul-II$75004 Paris France',
    115,48.852972,2.34991);

INSERT INTO place VALUES
   ('Circlestone',
    'Indian Ruins located on the second highest peak in the Superstition Wilderness of the Tonto National Forest. Leave Fireline at Turney Spring (33.487610, -111.132400)',
    'Hike',
    '',
    '',
    6000,33.477524,-111.134345);

INSERT INTO place VALUES
   ('Reavis-Ranch',
    'Historic Ranch in Superstition Mountains famous for Apple orchards',
    'Hike',
    '',
    '',
    5000,33.491154,-111.155385);

INSERT INTO place VALUES
   ('Rogers-Trailhead',
    'Trailhead for hiking to Rogers Canyon Ruins and Reavis Ranch',
    'Hike',
    '',
    '',
    4500,33.422212,-111.173393);

INSERT INTO place VALUES
   ('Reavis-Grave',
    'Grave site of Reavis Ranch Proprietor.',
    'Hike',
    '',
    '',
    3900,33.441499,-111.182511);

INSERT INTO place VALUES
   ('Muir-Woods',
    'Redwood forest North of San Francisco, surrounded by Mount Tamalpais State Park.',
    'Hike',
    'Muir Woods National Monument',
    '1 Muir Woods Rd$Mill Valley CA 94941',
    350,37.8912,-122.5957);

INSERT INTO place VALUES
   ('Windcave-Peak',
    'Beyond the Wind Cave is a half mile trail with 250 additional elevation to the peak overlooking Usery and the Valley.',
    'Hike',
    'Usery Mountain Recreation Area', 
    '3939 N Usery Pass Rd$Mesa AZ 85207',
    3130,33.476069,-111.595709);

