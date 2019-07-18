<?php

namespace PwExam\Model;

use DateTime;

final class Activity
{
    /** @var int */
    private $id;

    /** @var string */
    private $name;

    /** @var string */
    private $description;

    /** @var int */
    private $price;

    /** @var DateTime */
    private $createdAt;

    /** @var DateTime */
    private $activityTime;

    public function __construct(
        int $id,
        string $name,
        string $description,
        int $price,
        DateTime $createdAt,
        DateTime $activityTime
    ) {
        $this->id = $id;
        $this->name = $name;
        $this->description = $description;
        $this->price = $price;
        $this->createdAt = $createdAt;
        $this->activityTime = $activityTime;
    }

    /**
     * @return int
     */
    public function getId(): int
    {
        return $this->id;
    }

    /**
     * @return string
     */
    public function getName(): string
    {
        return $this->name;
    }

    /**
     * @return string
     */
    public function getDescription(): string
    {
        return $this->description;
    }

    /**
     * @return int
     */
    public function getPrice(): int
    {
        return $this->price;
    }

    /**
     * @return DateTime
     */
    public function getCreatedAt(): DateTime
    {
        return $this->createdAt;
    }

    /**
     * @return DateTime
     */
    public function getActivityTime(): DateTime
    {
        return $this->activityTime;
    }
}
